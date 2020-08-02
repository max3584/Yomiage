package org.SocketConnect;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 作り方要検討 <section>
 * <h1>ソケット通信用のクラス</h1>
 * <div>読み上げソフトウェアに対してソケット通信を行います。また作成時に以下のソースコードを参考にしています。</div>
 * <div>棒読み作成者：●●</div> <div> #copyright# </div> <section>
 * 
 * @author max
 */

public class YomiageSocketConnect {

	// init socket values
	private Socket socket;
	private PrintWriter pw;

	// init datas
	private short command;
	private short bytecode;
	private short speed;
	private short tone;
	private short volume;
	private short voice;

	// constructor
	public YomiageSocketConnect() throws UnknownHostException, IOException {
		this.command = 0;
		this.speed = -1;
		this.tone = -1;
		this.volume = -1;
		this.voice = 0;
		this.bytecode = 0;
		// socket class inits
		// ip address or severname , port number

		this.socket = new Socket("localhost", 50001);
		this.pw = new PrintWriter(this.socket.getOutputStream(), true);
	}

	public YomiageSocketConnect(short speed, short tone, short volume, short voice, short bytecode)
			throws UnknownHostException, IOException {
		this();
		this.bytecode = bytecode;
		this.speed = speed;
		this.tone = tone;
		this.volume = volume;
		this.voice = voice;
	}

	// execution
	public void RequestPacket(String msg) {
		int len = msg.getBytes().length;
		/*
		this.pw.write(String.format("%d%d%d%d%d%d %s", this.command, this.speed, this.tone, this.volume, this.bytecode, len, msg));
		*/
		// 順番固定
		byte[] msg_byte = msg.getBytes();
		this.pw.write(this.command); 	// コマンド（ 0:メッセージ読み上げ）
		this.pw.write(this.speed); 		// 速度 （-1:棒読みちゃん画面上の設定）
		this.pw.write(this.tone); 			// 音程 （-1:棒読みちゃん画面上の設定）
		this.pw.write(this.volume);		// 音量 （-1:棒読みちゃん画面上の設定）
		this.pw.write(this.voice); 		// 声質 （ 0:棒読みちゃん画面上の設定、1:女性1、2:女性2、3:男性1、4:男性2、5:中性、6:ロボット、7:機械1、8:機械2、10001～:SAPI5）
		this.pw.write(this.bytecode); 		// 文字列のbyte配列の文字コード(0:UTF-8, 1:Unicode, 2:Shift-JIS)
		this.pw.write(len); 				// 文字列のbyte配列の長さ
		for(byte bytecode : msg_byte)
			this.pw.write(bytecode & 0xFF); // 文字列のbyte配列
	}

	// close
	public void close() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// getter and setter

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public short getSpeed() {
		return speed;
	}

	public void setSpeed(short speed) {
		this.speed = speed;
	}

	public short getTone() {
		return tone;
	}

	public void setTone(short tone) {
		this.tone = tone;
	}

	public short getVolume() {
		return volume;
	}

	public void setVolume(short volume) {
		this.volume = volume;
	}

	public short getVoice() {
		return voice;
	}

	public void setVoice(short voice) {
		this.voice = voice;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public short getCommand() {
		return command;
	}

	public void setCommand(short command) {
		this.command = command;
	}

	public short getBytecode() {
		return bytecode;
	}

	public void setBytecode(short bytecode) {
		this.bytecode = bytecode;
	}

}
