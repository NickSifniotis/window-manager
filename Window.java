/**
 * Created by u5809912 on 9/03/16.
 */
public class Window
{
    public int x;
    public int y;
    public int x2;
    public int y2;

    public Window (int x, int y, int dx, int dy)
    {
        this.x = x;
        this.y = y;
        this.x2 = x + dx;
        this.y2 = y + dy;
    }

    public boolean sanity_check (int max_x, int max_y)
    {
        return (this.x >= 0 && this.x2 <= max_x && this.y >= 0 && this.y2 <= max_y);
    }

    public void display()
    {
        System.out.println(x + " " + y + " " + (x2 - x) + " " + (y2 - y));
    }
}
