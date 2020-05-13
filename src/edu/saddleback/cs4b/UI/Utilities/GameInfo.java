package edu.saddleback.cs4b.UI.Utilities;

/**
 * used for the table view
 */
public class GameInfo
{
    private String id;
    private String title;
    private String p1;
    private String p2;
    private String date;
    private String startTime;
    private String endTime;
    private String result;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getP1()
    {
        return p1;
    }

    public void setP1(String p1)
    {
        this.p1 = p1;
    }

    public String getP2()
    {
        return p2;
    }

    public void setP2(String p2)
    {
        this.p2 = p2;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
