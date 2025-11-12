package Screen;


import Util.Screen;

public interface IScreen
{
    public void init(ScreenManager manager);
    public Screen getScreenType();
    public void onShow();
}
