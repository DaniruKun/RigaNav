# RigaNav Android App
![RigaNav AppIcon](https://i.imgur.com/78CNovb.png)

An Android application built in Java to help tourists arriving at the Riga International Airport (RIX) quickly get relevant bus and other transport information.
## What it can do
The app displays the following information:
* Arrival times of the 22nd bus at the airport (up to 3 soonest).
* Departure times of the corresponding buses.
* Fare for taking the bus
* Fare of different taxi providers from the airport
## What it cannot do
* Fetch data from saraksts.rigassatiksme.lv
* Manually refresh screen
* Adjust screen XML data using a UI
## How it works
### Display
To display data on the main screen, a **TableLayout** object is used, which then displays other objects such as **TextView**s.
```java
TableLayout myTable = (TableLayout)findViewById(R.id.TableLayout1);
        myTable.setStretchAllColumns(true);
```
By using child elements such as **Rows** and **Columns**, new elements such as **TextViews** can be added if needed without interfering with the existing layout properties.
```java
TableRow busRow = new TableRow(this);
        myTable.addView(busRow);
        busRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView busText = new TextView(this);
        busText.setText("Bus No 22");
        busText.setTextSize(24);
        busRow.addView(busText);

        TextView leavesInText = new TextView(this);
        leavesInText.setText("Leaves in");
        leavesInText.setTextSize(24);
        busRow.addView(leavesInText);
```
A child element of **TableLayout** - **TableRow** is used and added to the screen using the **addView()** method.

### Time
To find out the local time, the app uses the **Calendar** object and the **get()** method to write the object's properties to corresponding variables of type **Int**.
```java
Calendar c = GregorianCalendar.getInstance();
```
To account for weekends, as well as Latvian holidays, a local file /main/assets/atviaHolidays.txt directory is read with holiday dates written in the following format: **"DD MM YYYY"** as strings, with each entry on its own separate line.

`1 1 2017`

A **BufferedReader** object is used to read files only once and parse the data into **ArrayList** structures. 
## Test Cases
![Test Cases](https://i.imgur.com/RiQ3ecf.png)
