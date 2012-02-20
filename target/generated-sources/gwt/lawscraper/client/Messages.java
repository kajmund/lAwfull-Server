package lawscraper.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/Users/erik/devel/law/src/main/resources/lawscraper/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Enter your name".
   * 
   * @return translated "Enter your name"
   */
  @DefaultMessage("Enter your name")
  @Key("nameField")
  String nameField();

  /**
   * Translated "Skrapa".
   * 
   * @return translated "Skrapa"
   */
  @DefaultMessage("Skrapa")
  @Key("sendButton")
  String sendButton();
}
