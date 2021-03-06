/*
 * GNU LESSER GENERAL PUBLIC LICENSE
 *                       Version 3, 29 June 2007
 *
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 *
 * You can view LICENCE file for details. 
 *
 * @author The Dragonet Team
 */
package org.dragonet.proxy.commands;

import org.dragonet.proxy.DragonProxy;

import java.util.Scanner;

public class ConsoleCommandReader {

    private final DragonProxy proxy;

    public ConsoleCommandReader(DragonProxy proxy) {
        this.proxy = proxy;
    }

    public void startConsole() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                String command = "";
                while (!proxy.isShuttingDown()) {
                    try {
//                        System.out.print(">");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNext()) {
                            command = scanner.nextLine();

                            if (command != null || command.trim().length() != 0) {
                                proxy.getLogger().info("[Console] Executing command: " + command);
                                proxy.getCommandRegister().callCommand(command);
                            }
                        }
                        Thread.sleep(50);
                    } catch (Exception ex) {
                        proxy.getLogger().severe("Error while executing command: " + ex);
                        ex.printStackTrace();
                    }
                }
            }
        });

        thread.setName("ConsoleCommandThread");
        thread.setDaemon(true);
        thread.start();
    }
}
