package Client;

import Client.Handler.JsonDecoder;
import Client.Handler.JsonEncoder;
import Client.Lite.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Client {
    Scanner sc = new Scanner(System.in);
    String name = null;
    int id;
    String nameT = null;
    boolean tmp = true;

    public static void main(String[] args) throws InterruptedException {
        new Client().run();
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap()
                .group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new LengthFieldBasedFrameDecoder(512 * 512, 0, 2, 0, 2),
                                new LengthFieldPrepender(2),

                                new JsonEncoder(),
                                new JsonDecoder(),

                                new SimpleChannelInboundHandler<Message>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
                                        if (msg instanceof FileMessage) {
                                            var message = (FileMessage) msg;
                                            try (final RandomAccessFile randomAccessFile = new RandomAccessFile(message.getName(), "rw")) {
                                                randomAccessFile.write(message.getContent());
                                            }
                                        } else {
                                            System.out.println(msg.getText());
                                        }
                                    }
                                }


                        );
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true);

        Channel channel = bootstrap.connect("localhost", 8080).sync().channel();

        while (true) {
            if (tmp) {
                System.out.println("use the /help command for help");
                tmp = false;
            }

            System.out.print("Write msg: ");
            String msg = sc.nextLine();

            if (msg.equals("/dd")) {
                System.out.print("enter the number you want to download: ");
                id = sc.nextInt();
                final DownloadFileRequestMessage message = new DownloadFileRequestMessage();
                message.setId(id);
                channel.writeAndFlush(message);
            }
            if (msg.equals("/ud")) {
                System.out.print("enter the full path of the file: ");
                nameT = sc.nextLine();
                try (RandomAccessFile accessFile = new RandomAccessFile(nameT, "r")) {
                    final FileMessage fileMessage = new FileMessage();
                    accessFile.length();
                    byte[] content = new byte[(int) accessFile.length()];
                    accessFile.read(content);

                    fileMessage.setName(new File(nameT).getName());
                    fileMessage.setContent(content);
                    channel.writeAndFlush(fileMessage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!msg.equals("/ud") && !msg.equals("/dd")) {
                final TextMessage message = new TextMessage();
                message.setText(msg);
                channel.writeAndFlush(message);
            }
        }

    }
}
