package railroad.websockets;

import org.apache.log4j.Logger;
import railroad.config.CustomSpringConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import railroad.service.TrainService;

import java.io.Serializable;
import java.util.ArrayList;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@Service
@Configurable
@ServerEndpoint(value = "/notification", configurator = CustomSpringConfigurator.class)
public class NotificationEndPoint implements Serializable {

  /**
   * EndPoint for listening events from TimeBoard app
   */

  private static final Logger log = Logger.getLogger(NotificationEndPoint.class);

  static ArrayList<Session> sessions = new ArrayList<>();
  public static ArrayList<Session> getSessions() {
    return sessions;
  }
  public static void setSessions(ArrayList<Session> sessions) {
    NotificationEndPoint.sessions = sessions;
  }

  @Autowired
  TrainService trainService;

  /**
   * Receives data and parses it to station name and page number.
   *
   * @param message message in format stationName/pageNumber
   */
  @OnMessage
  public void onMessage(String message) {
    String[] dataReceived = message.split("/");
    trainService.trainsByStationTB(dataReceived[0], Integer.parseInt(dataReceived[1]));
    log.info("endpoint got request from timeboard: " + message);
  }

  @OnOpen
  public void onOpen(Session session) {
    sessions.add(session);
  }

  @OnClose
  public void onClose(Session session) {
    sessions.remove(session);
  }


}
