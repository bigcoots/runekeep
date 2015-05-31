package com.rs.net.encoders;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;

import com.rs.game.player.Player;
import com.rs.io.OutputStream;
import com.rs.net.Session;
import com.rs.utils.Utils;

/**
* CREDITS:
* @Sean
* @Thomas (KTHILLMAN)
**/

public final class LoginPacketsEncoder extends Encoder {

	public LoginPacketsEncoder(Session connection) {
		super(connection);
	}

	public final void sendStartUpPacket() {
		OutputStream stream = new OutputStream(1);
		stream.writeByte(0);
		session.write(stream);
	}

	public final void sendClientPacket(int opcode) {
		OutputStream stream = new OutputStream(1);
		stream.writeByte(opcode);
		ChannelFuture future = session.write(stream);
		if (future != null) {
			future.addListener(ChannelFutureListener.CLOSE);
		} else {
			session.getChannel().close();
		}
	}

	public void sendLobbyDetails(Player player) {
		int ipHash = 0;
		OutputStream stream = new OutputStream();
		stream.writePacketVarByte(player, 2);
		stream.writeByte(player.getRights());//Player Rights
		stream.writeByte(0);
		stream.writeByte(0);
		stream.write24BitInteger(0);
		stream.writeByte(0);
		stream.writeByte(0);
		stream.writeByte(0);
		stream.writeLong(500); //When Subscription Ends
		stream.write5ByteInteger(12);
		stream.writeByte(0x2); //0x1 - IF MEMBERS, 0x2 - SUBSCRIPTION
		stream.writeInt(1); 
		stream.writeByte(1); 
		stream.writeInt(0); //Recovery Questions Set (DATE)
		stream.writeShort(1); //Recovery Questions (0 - NOT SET; 1 - SET)
		stream.writeShort(0); //Messages Add Support for Forum Integration
		stream.writeShort(player.getLastLoggedIn() == 0 ? 0 : (int)(((player.getLastLoggedIn() - 1014786000000L) / 86400000) + 1));//Last Login Date
		if (player.getLastIP() != null) {
			String[] ipSplit = player.getLastIP().split("\\.");
			ipHash = Integer.parseInt(ipSplit[0]) << 24 | Integer.parseInt(ipSplit[1]) << 16 | Integer.parseInt(ipSplit[2]) << 8 | Integer.parseInt(ipSplit[3]);
		}
		stream.writeInt(ipHash); //IP Part
		stream.writeByte(3); //Email Status (0 - NO EMAIL; 1 - PENDING PARENTAL CONSENT; 2 - PENDING CONFIRMATION; 3 - REGISTERED)
		stream.writeShort(0);
		stream.writeShort(0);
		stream.writeByte(0);
		stream.writeGJString(player.getDisplayName());
		stream.writeByte(0);
		stream.writeInt(1);
		stream.writeByte(1);
		stream.writeShort(1); //Default World ID
		stream.writeGJString("127.0.0.1");
		stream.endPacketVarByte();
		player.getSession().write(stream);     
	}

	public final void sendLoginDetails(Player player) {
		OutputStream stream = new OutputStream();
		stream.writePacketVarByte(player, 2);
		stream.writeByte(player.getRights());
		stream.writeByte(0);
		stream.writeByte(0);
		stream.writeByte(0);
		stream.writeByte(1);
		stream.writeByte(0);
		stream.writeShort(player.getIndex());
		stream.writeByte(1);
		stream.write24BitInteger(0);
		stream.writeByte(1); //Type of World (0 - Free Players; 1 - Members)
		stream.writeString(player.getDisplayName());
		stream.endPacketVarByte();
		session.write(stream);
	}
}