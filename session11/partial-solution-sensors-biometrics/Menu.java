public interface Menu {

    public void showWelcome();
    public void showMenu();

    public void show(String message);
    public void showError(String message);

    public String getString();

    // cosas mas especificas...
    public Integer getInteger();

}
