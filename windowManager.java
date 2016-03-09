import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u5809912 on 9/03/16.
 */
public class windowManager
{
    private static List<Window> window_list;
    private static int x_max;
    private static int y_max;


    private static void open (int x, int y, int dx, int dy)
    {
        Window newbie = new Window (x, y, dx, dy);
        int result = get_colliding_window(newbie);

        if (result != -1 || !newbie.sanity_check(x_max, y_max))
            System.out.println("Command " + 0 + ": OPEN - window does not fit");
        else
            window_list.add(newbie);
    }

    private static void close (int x, int y)
    {
        int target = get_window_at_point(x, y);
        if (target == -1)
            System.out.println("Command " + 0 + ": CLOSE - no window at given position");
        else
            window_list.remove(target);
    }

    private static void resize (int x, int y, int nu_dx, int nu_dy)
    {
        // find me.
        int me = get_window_at_point(x, y);

        Window myself = window_list.remove(me);
        Window hypothetical_me = new Window (myself.x, myself.y, nu_dx, nu_dy);

        int result = get_colliding_window(hypothetical_me);
        if (result == -1 && hypothetical_me.sanity_check(x_max, y_max))
            window_list.add(me, hypothetical_me);
        else
        {
            // remember to replace the original window in the list ...
            window_list.add(me, myself);
            System.out.println("Command " + 0 + ": RESIZE - window does not fit");
        }
    }

    private static void move (int x, int y, int dx, int dy)
    {
        // rotate the world

        // apply the transformation

        // un-rotate the world

        System.out.println("Moving! " + x + ":" + y + ":" + dx + ":" + dy);
    }

    private static void readInput()
    {
        String str;
        String [] parts;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            str = input.readLine();
            parts = str.split(" ");
            x_max = Integer.parseInt(parts[0]);
            y_max = Integer.parseInt(parts[1]);

            while ((str = input.readLine()) != null)
            {
                parts = str.split(" ");

                switch(parts[0])
                {
                    case "OPEN":
                        open (Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                        break;
                    case "CLOSE":
                        close (Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                    case "MOVE":
                        move (Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                        break;
                    case "RESIZE":
                        resize (Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                        break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error!");
        }
    }

    private static int get_window_at_point (int x, int y)
    {
        Window guyman = new Window (x, y, 0, 0);
        return get_colliding_window(guyman);
    }

    private static int get_colliding_window (Window a)
    {
        int result = -1;

        Window[] windows = new Window[window_list.size()];
        window_list.toArray(windows);
        for (int i = 0; i < windows.length; i ++)
            if (is_collision (a, windows[i]))
                result = i;

        return result;
    }

    private static boolean is_collision (Window a, Window b)
    {
        Window left_most = (a.x < b.x) ? a : b;
        Window right_most = (a.x < b.x) ? b : a;

        Window top_most = (a.y < b.y) ? a : b;
        Window bottom_most = (a.y < b.y) ? b : a;

        return (left_most.x <= right_most.x) && (left_most.x2 > right_most.x)
                && (top_most.y <= bottom_most.y) && (top_most.y2 > bottom_most.y);
    }


    public static void main(String[] args)
    {
        window_list = new ArrayList<>();

        readInput();

        System.out.println(window_list.size() + " window(s):");
        for (Window w: window_list)
            w.display();
    }
}
