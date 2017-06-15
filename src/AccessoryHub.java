import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Robert on 14/01/2017.
 */
public class AccessoryHub {

    public static void main(String[] args) {
        String destination = "10.0.0.100:8888";


        if (args.length > 0) {
            destination = args[0];
        }

        DataHandler handler = new DataHandler();
        System.out.println("available commands:");
        String[] commands = handler.getAvailableCommands();
        for (int i = 0; i < commands.length; i++) {
            System.out.println(commands[i]);
        }


        try {
            final Socket socket = IO.socket("http://" + destination);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("connected to server");
                }
            });
            socket.on("dataRequest", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("data requested");
                    try {
                        JSONObject res = new JSONObject();
                        res.put("data", handler.getAvailableCommands());
                        socket.emit("data", res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            socket.on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject) args[0];
                    try {

                        handler.executeCommand(obj.getString("data"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("disconnected");
                }
            });
            socket.connect();
            System.out.println("client started");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
