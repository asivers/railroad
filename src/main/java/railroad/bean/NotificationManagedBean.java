package railroad.bean;

import railroad.websockets.NotificationEndPoint;
import railroad.websockets.ThreadMsgSender;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "notificationManagedBean")
@ViewScoped
public class NotificationManagedBean implements Serializable{

  /**
   * Creates a new instance of NotificationManagedBean
   */
  public NotificationManagedBean() {
  }
  
  String message;
  
  public void sendNotification()  {
    System.out.println(777);
    System.out.println("Sending Message: " + message);
    ThreadMsgSender.sendMessage(message);
  }
  
  public List getNotificationReceiverList() {
    System.out.println(888);
    return NotificationEndPoint.getSessions();
  }

  public String getMessage() {
    System.out.println(999);
    return message;
  }

  public void setMessage(String message) {
    System.out.println(9999);
    this.message = message;
  }
  
  
}
