# Classes. Encapsulation. Exceptions
## Task 6

Suppose, we have the **Plant** class from the previous task, which includes **private** fields **String name**, **Color color** and **Type type**, and constructor with three **String** parameters where these fields are initialized.

**Color** and **Type** are **Enum**.

**Color** contains **WHITE**, **RED**, **BLUEe** entries.

**Type** contains **RARE** and **ORDINARY** entries.

We also have the classes **ColorException** and **TypeException** derived from **Exception**.

The constructor of **Plant** throws a corresponding exception whenever an inappropriate parameter is passed: **String type**, **String color**, **String name**.

Write a **static** method **tryCreatePlant**  that takes 3 string parameters - **type**, **color** and **name** and returns an instance of **Plant**, created based on passed params. (**Don't create any new classes, write as if you are already inside a class.**)

The **tryCreatePlant** method should catch exceptions that can be thrown by **Plant** constructor.

If inappropriate type passed as a parameter, a **Plant** object should be returned anyway, with **Ordinary** type.

If an inappropriate color is passed, set **Red** color for created instance.
