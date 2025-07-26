# Classes. Encapsulation. Exceptions

## Task 2

The task is to create a **Point** class that models a 2D point with x and y coordinates.

![image](https://user-images.githubusercontent.com/61456363/168443472-b2507d4a-73da-470c-ba21-f25e18fdcaf9.png)

The **Point** class should include the following:

1. Two **private** instance variables **x** and **y** of type **int**.
2. A constructor that initializes the point with the given **x** and **y** coordinates.
3. A method **getXYPair()** that returns the **x** and **y** values as a 2-element **int** array.
4. A method **distance(int x, int y)** that calculates the distance from this point to another point with the given **(x, y)** coordinates.
5. An overloaded method **distance(Point point)** that calculates the distance from this point to a given **Point** object.
6. Another overloaded method **distance()** that calculates the distance from **this** point to the origin **(0, 0)**.

> **Important:** Do not use System.out.print or System.out.println in your code to ensure all tests pass.
