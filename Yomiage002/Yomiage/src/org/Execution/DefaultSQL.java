package org.Execution;

public class DefaultSQL {

	private String sql;
	private String prevDelete;
	private String updateTables;

	public DefaultSQL() {
		/**
		 * ＳＱＬファイルを別途で用意しておくので中身を知りたい場合はそちらを参照してください
		 * 
		 */
		this.prevDelete = /* exceptionReferenceData を更新するときに使用する */
				"create table RecoveryTable (\r\n" + "	comments text,\r\n"
				+ "	flg number(1) default 0 not null,\r\n" + "	priority number(100) not null\r\n"
				+ "	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n"
				+ "	constraint use_com_ork primary key (comments)\r\n" + ");" +
				/**
				 * ReferenceData Recovery
				 */
				"create table RecoveryTable2 (\r\n" + "	comments text,\r\n" + "	totals text,\r\n"
				+ "	percent number(3,2) not null,\r\n"
				+ "	constraint use_com_prk primary key (comments)\r\n" + ");" +
				/**
				 * ExceptionReferenceDataのバックアップ
				 */
				"insert into RecoveryTable (comments, flg, priority) select * from exceptionreferenceData;"
				+
				/**
				 * ReferenceDataのバックアップ
				 */
				"insert into RecoveryTable2 (comments, totals, percent) select * from referenceData;\r\n"
				/**
				 * ExceptionReferenceDataの実行
				 */
				+ "drop table exceptionreferencedata;"
				+ "drop table referencedata;"
				+ "drop view referencedataview;";
		
		/*
		 * ReferenceData
		 */
		this.updateTables = "\r\n" + 
				"drop view countdata;\r\n" + 
				"\r\n" + 
				"CREATE VIEW countData (\r\n" + 
				"	username,\r\n" + 
				"	comments,\r\n" + 
				"	counter\r\n" + 
				") as \r\n" + 
				"select username, comments, count(comments) as counter\r\n" + 
				"	from Natu_data\r\n" + 
				"		group by username, comments\r\n" + 
				"		having count(comments) > 1\r\n" + 
				"		order by counter;\r\n" + 
				"\r\n" + 
				"/*\r\n" + 
				"テーブル更新する場合に使用します\r\n" + 
				"*/\r\n" + 
				"create table RecoveryTable (\r\n" + 
				"	comments text,\r\n" + 
				"	flg number(1) default 0 not null,\r\n" + 
				"	priority number(100) not null\r\n" + 
				"	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n" + 
				"	constraint use_com_ork primary key (comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"/* exceptionReferenceData を更新するときに使用する */\r\n" + 
				"create table RecoveryTable2 (\r\n" + 
				"	comments text,\r\n" + 
				"	totals text,\r\n" + 
				"	percent number(3,2) not null,\r\n" + 
				"	constraint use_com_prk primary key (comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"/*\r\n" + 
				"リカバリー用のテーブル\r\n" + 
				"*/\r\n" + 
				"insert into RecoveryTable2 (comments, totals, percent) select * from referenceData;\r\n" + 
				"\r\n" + 
				"insert into RecoveryTable (comments, flg, priority) select * from exceptionreferenceData;\r\n" + 
				"\r\n" + 
				"drop table referencedata;\r\n" + 
				"\r\n" + 
				"drop table exceptionreferencedata;\r\n" + 
				"\r\n" + 
				"create table referenceData (\r\n" + 
				"	comments text,\r\n" + 
				"	totals text,\r\n" + 
				"	percent number(3,2) not null,\r\n" + 
				"	constraint use_com_prk primary key (comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"create table exceptionreferenceData (\r\n" + 
				"	comments text,\r\n" + 
				"	flg number(1) default 0 not null,\r\n" + 
				"	priority number(100) not null\r\n" + 
				"	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n" + 
				"	constraint use_com_ork primary key (comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"/* \r\n" + 
				"解析データを簡易的に出力するＳＱＬ \r\n" + 
				"	統計データからのもので、そんな複雑な式は書いていない\r\n" + 
				"*/\r\n" + 
				"\r\n" + 
				"drop view referencedataview;\r\n" + 
				"\r\n" + 
				"create view referenceDataView (comments, totals, percent) as\r\n" + 
				"select t1.comments, t1.totals, t1.persent \r\n" + 
				"	from\r\n" + 
				"		(\r\n" + 
				"			select a.comments, sum(b.counter) as totals, round(cast(a.counter as real) / sum(b.counter),10) as persent\r\n" + 
				"				from \r\n" + 
				"					countdata a,\r\n" + 
				"					countdata b\r\n" + 
				"				where\r\n" + 
				"					a.username = b.username\r\n" + 
				"				group by\r\n" + 
				"					a.comments\r\n" + 
				"		) t1,\r\n" + 
				"		natu_data natu\r\n" + 
				"	where\r\n" + 
				"		natu.comments = t1.comments and\r\n" + 
				"		t1.persent > 0.001 and\r\n" + 
				"		t1.totals > 5 and\r\n" + 
				"		natu.groups in ('PUBLIC', 'PARTY')\r\n" + 
				"	group by\r\n" + 
				"		t1.comments\r\n" + 
				"	order by\r\n" + 
				"		t1.persent asc, t1.totals desc;\r\n" + 
				"\r\n" + 
				"/* データ挿入時の・・・sql(念のため) */\r\n" + 
				"\r\n" + 
				"insert into referenceData (comments, totals, percent)\r\n" + 
				"	select rf.comments, rf.totals, rf.percent * 100\r\n" + 
				"		from referencedataview rf\r\n" + 
				"	where \r\n" + 
				"		not exists (\r\n" + 
				"			select *\r\n" + 
				"				from referencedata\r\n" + 
				"		);\r\n" + 
				"\r\n" + 
				"drop table RecoveryTable2;\r\n" + 
				"drop table RecoveryTable;";
		this.sql = "/* データベースのテーブル作成用のデータ */\r\n" +
		/**
		 * Main Table Natu_data
		 */
				"create table Natu_data(\r\n" + "	tmstamp text,\r\n" + "	number NUMERIC(5),\r\n"
				+ "	groups text not null,\r\n" + "	sirial NUMERIC(8) not null,\r\n"
				+ "	username text not null,\r\n" + "	comments text not null,\r\n"
				+ "	constraint tm_num_prk primary key (tmstamp, number),\r\n"
				+ "	constraint group_chk check (groups in ('PUBLIC', 'REPLY', 'GUILD', 'PARTY', 'GROUP'))"
				+ ");" +
				/**
				 * countData
				 */
				"CREATE VIEW countData (\r\n" + "	username,\r\n" + "	comments,\r\n" + "	counter\r\n"
				+ ") as \r\n" + "select username, comments, count(comments) as counter\r\n"
				+ "	from Natu_data\r\n" + "		group by username, comments\r\n"
				+ "		having count(comments) > 1\r\n" + "		order by counter;\r\n"
				+ "\r\n" +
				/**
				 * ReferenceData
				 */
				"create table referenceData (\r\n" + "	comments text,\r\n" + "	totals text,\r\n"
				+ "	percent number(3,2) not null,\r\n"
				+ "	constraint use_com_prk primary key (comments)\r\n" + ");\r\n" + "\r\n" +
				/**
				 * ExceptionReferenceData
				 */
				"create table exceptionreferenceData (\r\n" + "	comments text,\r\n"
				+ "	flg number(1) default 0 not null,\r\n" + "	priority number(100) not null\r\n"
				+ "	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n"
				+ "	constraint use_com_ork primary key (comments)\r\n" + ");"
				/**
				 * Create Reference View
				 */
				+ "create view referenceDataView (comments, totals, percent) as\r\n"
				+ "select t1.comments, t1.totals, t1.persent \r\n" + "	from\r\n" + "		(\r\n"
				+ "			select a.comments, sum(b.counter) as totals, round(cast(a.counter as real) / sum(b.counter),10) as persent\r\n"
				+ "				from \r\n"
				+ "					countdata a,\r\n"
				+ "					countdata b\r\n" + "				where\r\n"
				+ "					a.username = b.username\r\n"
				+ "				group by\r\n" + "					a.comments\r\n"
				+ "		) t1,\r\n" + "		natu_data natu\r\n" + "	where\r\n"
				+ "		natu.comments = t1.comments and\r\n" + "		t1.persent > 0.001 and\r\n"
				+ "		t1.totals > 5 and\r\n" + "		natu.groups in ('PUBLIC', 'PARTY')\r\n"
				+ "	group by\r\n" + "		t1.comments\r\n" + "	order by\r\n"
				+ "		t1.persent asc, t1.totals desc;";
	}

	// getter and setter
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getPrevDelete() {
		return prevDelete;
	}

	public void setPrevDelete(String prevDelete) {
		this.prevDelete = prevDelete;
	}

	public String getUpdateTables() {
		return updateTables;
	}

	public void setUpdateTables(String updateTables) {
		this.updateTables = updateTables;
	}
}
