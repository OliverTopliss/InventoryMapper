import java.awt.Point;
//class that is used to model a point on the map
public class MapPoint extends Point implements Comparable<MapPoint>
{
  private int xCoordinate = 0;
  private int yCoordinate = 0;
  private String name = "";
  private String location = "";
  private String typeOfDevice = "";


  public MapPoint(int xCoordinate, int yCoordinate, String name, String location, String typeOfDevice)
  {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.name = name;
    this.location = location;
    this.typeOfDevice = typeOfDevice;
  }//MapPoint

  @Override
  public int compareTo(MapPoint other)
  {
    if(other.getXCoordinate() < xCoordinate || other.getYCoordinate() < yCoordinate)
    {
      return -1;
    }// if
    else if(other.getXCoordinate() == xCoordinate || other.getYCoordinate() == yCoordinate)
    {
      return 0;
    }//else if
    else
    {
      return 1;
    }//else
  }//compareTo


  //the string format that the object should posses (overridden from Object)
  @Override
  public String toString()
  {
    return xCoordinate + ", " + yCoordinate + ", " + name + ", " + location + " " + typeOfDevice;
  }//toString

  //accessor method for the X Coordinate
  public int getXCoordinate()
  {
    return xCoordinate;
  }//getXCoordinate

  //accessor method for the Y Coordinate
  public int getYCoordinate()
  {
    return yCoordinate;
  }//getYCoordinate

  public String getName()
  {
    return name;
  }//getName

  public String getPointLocation()
  {
    return location;
  }//getDetails

  public String getTypeOfDevice()
  {
    return typeOfDevice;
  }//getTypeOfDevice
}//MapPoint class