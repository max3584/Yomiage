/* データベースのテーブル作成用のデータ */

drop table Natu_data;

drop table referencedata;

drop table exceptionreferencedata;

create table Natu_data(
	tmstamp text,
	number NUMERIC(5),
	groups text not null,
	sirial NUMERIC(8) not null,
	username text not null,
	comments text not null,
	constraint tm_num_prk primary key (tmstamp, number),
	constraint group_chk check (groups in ('PUBLIC', 'REPLY', 'GUILD', 'PARTY', 'GROUP'))
);

CREATE VIEW countData (
	username,
	comments,
	counter
) as 
select username, comments, count(comments) as counter
	from Natu_data
		group by username, comments
		having count(comments) > 1
		order by counter;

create table referenceData (
	comments text,
	totals text,
	percent number(3,2) not null,
	constraint use_com_prk primary key (comments)
);

create table exceptionreferenceData (
	comments text,
	flg number(1) default 0 not null,
	priority number(100) not null
	constraint flg_chk check(flg >= 0 and flg <= 1),
	constraint use_com_ork primary key (comments)
);

/* 
解析データを簡易的に出力するＳＱＬ 
	統計データからのもので、そんな複雑な式は書いていない
*/

drop view referencedataview;

create view referenceDataView (comments, totals, percent) as
select t1.comments, t1.totals, t1.persent 
	from
		(
			select a.comments, sum(b.counter) as totals, round(cast(a.counter as real) / sum(b.counter),10) as persent
				from 
					countdata a,
					countdata b
				where
					a.username = b.username
				group by
					a.comments
		) t1,
		natu_data natu
	where
		natu.comments = t1.comments and
		t1.persent > 0.001 and
		t1.totals > 5 and
		natu.groups in ('PUBLIC', 'PARTY')
	group by
		t1.comments
	order by
		t1.persent asc, t1.totals desc;

/* データ挿入時の・・・sql(念のため) */

insert into referenceData (comments, totals, percent)
	select rf.comments, rf.totals, rf.percent * 100
		from referencedataview rf
	where 
		not exists (
			select *
				from referencedata
		);

/*
テーブル更新する場合に使用します
*/
create table RecoveryTable (
	comments text,
	flg number(1) default 0 not null,
	priority number(100) not null
	constraint flg_chk check(flg >= 0 and flg <= 1),
	constraint use_com_ork primary key (comments)
);

/* exceptionReferenceData を更新するときに使用する */
create table RecoveryTable2 (
	comments text,
	totals text,
	percent number(3,2) not null,
	constraint use_com_prk primary key (comments)
);

/*
リカバリー用のテーブル
*/
insert into RecoveryTable2 (comments, totals, percent) select * from referenceData;

insert into RecoveryTable (comments, flg, priority) select * from exceptionreferenceData;

/*

リカバリーから戻す作業

*/

drop table RecoveryTable;

insert into referenceData (comments, totals, percent) select * from RecoveryTable2;

insert into exceptionreferenceData (comments, flg, priority) select * from RecoveryTable;

drop table RecoveryTable2;
drop table RecoveryTable;
