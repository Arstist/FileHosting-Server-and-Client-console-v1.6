package Server.Handler;

import Client.Lite.*;
import Server.Command;
import Server.Files.Class.FileLooker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.RandomAccessFile;
import java.time.LocalDateTime;

public class LiteServerChannelInboundHandler extends SimpleChannelInboundHandler<Message> {
    final private String pathSave = "D:\\JavaProject\\Echo-Server-Netty\\src\\main\\java\\Server\\Files\\";


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is registered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is un    registered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is inactive");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (msg instanceof TextMessage) {
            Command command = new Command();
            var message = (TextMessage) msg;
            System.out.println("incoming text message: " + message.getText());

            if (command.checker(message.getText())) {
                ((TextMessage) msg).setText(command.checkCommand(message.getText()));
            }
            ctx.writeAndFlush(msg);

        }
        if (msg instanceof DateMessage) {
            var message = (DateMessage) msg;
            System.out.println("incoming date message: " + message.getDate());
            ctx.writeAndFlush(msg);
        }

        if (msg instanceof DownloadFileRequestMessage) {
            var message = (DownloadFileRequestMessage) msg;
            try (RandomAccessFile accessFile = new RandomAccessFile(FileLooker.converter(message.getId()), "r")) {
                final FileMessage fileMessage = new FileMessage();
                accessFile.length();
                byte[] content = new byte[(int) accessFile.length()];
                accessFile.read(content);
                fileMessage.setName(FileLooker.converterForName(message.getId()));
                fileMessage.setContent(content);
                ctx.writeAndFlush(fileMessage);
            }
        }

        if (msg instanceof FileMessage) {
            var message = (FileMessage) msg;
            try (final RandomAccessFile randomAccessFile = new RandomAccessFile(pathSave + message.getName(), "rw")) {
                randomAccessFile.write(message.getContent());
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Catch cause " + cause.getMessage());
        ctx.close();
    }

    public String getDate() {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%s/%s/%s | %s:%s", now.getDayOfMonth(), now.getMonthValue(), now.getYear(), now.getHour(), now.getMinute());
    }
}
