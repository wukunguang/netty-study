package com.magicguang.netty.helloword.server;

import io.netty.channel.*;

import java.net.InetAddress;
import java.util.Date;

@ChannelHandler.Sharable
public class HelloWorldServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\n");
        ctx.write("It is " + new Date() + "now. \r\n");
        System.out.println("new Client in:  " + ctx.channel().id());
        ctx.flush();
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        String respString;
        boolean close = false;
        if (s.isEmpty()){
            respString = "please send some text to server?\r\n";
        } else if (s.equals("exit")){
            close = true;
            respString = "bye~!\r\n";
        } else {
            respString = "Server is receive your text: " + s + "\r\n";
        }
        ChannelFuture channelFuture = channelHandlerContext.write(respString);
        if (close){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client: " + ctx.channel().id() + " is exit!");
        super.channelInactive(ctx);
    }
}
