# Project-Management
This repository contains the source code for a Java application designed to manage project data. The application features a graphical user interface (GUI) built using the Swing library.

Project Entities:

Employee: Represented by employee ID, name, surname, and salary.
Manager: Inherits attributes from Employee and adds a department attribute. Managers can be assigned or unassigned employees.
Salesperson: Inherits attributes from Employee and introduces additional attributes for workplace and absences.
Product: Contains attributes for name, price, unit of measure, country of origin, and model.
Perishable Product: Inherits attributes from Product and adds attributes for expiration date and storage instructions.
Technical Product: Inherits attributes from Product and adds attributes for dimensions, nominal power, and voltage.

Features:

User-friendly GUI for adding, deleting, modifying, and viewing all managed objects.
Tabular representation of each object type.
Dedicated forms for adding and editing objects, tailored to the specific object type.
Integrated forms and tables for each object type on separate tabs.
Quick search functionality to find objects based on a keyword entered in a text field. Search results are displayed in a separate table.
Ability to save and load all object data in a text file format.
Single file stores all object types, with each object represented on a separate line. Attributes are separated by a pipe symbol ("|"). Groups of different object types are separated by a dollar sign ("$").
This application provides a comprehensive solution for managing project data, offering a user-friendly interface, efficient data management functionalities, and easy data persistence.
