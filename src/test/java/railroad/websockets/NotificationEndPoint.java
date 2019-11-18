package railroad.websockets;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import railroad.bean.NotificationManagedBean;
import railroad.model.additional.TrainTime;
import railroad.service.TrainService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Service
@Configurable
@ServerEndpoint(value = "/notification", configurator = SpringConfigurator.class)
public class NotificationEndPoint implements Serializable {

  private TrainService trainService;
  @Inject
  public void setTrainService(TrainService trainService) {
    this.trainService = trainService;
  }

  private NotificationManagedBean notificationManagedBean = new NotificationManagedBean();

  public void toTimeBoard(String stationName, int page) {
    int trainsCount = trainService.trainsByStationCount(stationName);
    int pagesCount = (trainsCount + 6) / 7;
    List<TrainTime> toTimeBoard = trainService.trainsByStation(stationName, page);
    String toTimeBoardString = stationName + "/" + pagesCount + "/";
    for (TrainTime trainTime : toTimeBoard) {
      toTimeBoardString += trainTime.getNumber();
      toTimeBoardString += "/";
      toTimeBoardString += trainTime.getTime();
      toTimeBoardString += "/";
    }
    notificationManagedBean.setMessage(toTimeBoardString);
    notificationManagedBean.sendNotification();
  }

  static ArrayList<Session> sessions = new ArrayList<>();

//   static ThreadMsgSender sender = new ThreadMsgSender();
//   static  {
//   sender.start();
//   }

  @OnMessage
  public void messageReceiver(String message) {
    System.out.println(333);
    System.out.println("Received message:" + message);
    String[] dataReceived = message.split("/");
    toTimeBoard(dataReceived[0], Integer.parseInt(dataReceived[1]));
  }

  @OnOpen
  public void onOpen(Session session) {
    System.out.println(444);
    System.out.println("onOpen: " + session.getId());
    sessions.add(session);
    System.out.println("onOpen: Notification list size: " + sessions.size());
    notificationManagedBean.setMessage("start");
    notificationManagedBean.sendNotification();
  }

  @OnClose
  public void onClose(Session session) {
    System.out.println(555);
    System.out.println("onClose: " + session.getId());
    sessions.remove(session);
  }

  public static ArrayList<Session> getSessions() {
    System.out.println(666);
    return sessions;
  }

  public static void setSessions(ArrayList<Session> sessions) {
    System.out.println(6666);
    NotificationEndPoint.sessions = sessions;
  }

}
