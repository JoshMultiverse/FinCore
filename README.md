_FinCore Budget Management System_

In this project my objectives include:
- Creating a fully functional console-based budget management system
- Using complex data structures such as Linked Lists to enable efficiency within this project
- Ensuring the application is user friendly and easy to understand

_Development Enviroment_

IDE

Within FinCore, I have decided to use **Visual Studio Code** as my IDE. This is because it is very user-friendly and is an application which I am already familiar with, meaning I can maximise efficiency when developing the project. It also means that I do not have to learn any new keyboard shortcuts while in development, allowing me to work more on my code and less on learning shortcuts.

JDK

I have also chose to use **OpenJDK** as my **Java Development Kit,** with Java 21 installed. This is mainly due to its lack of licensing due to it being open sourced, which makes it a very cost-effective solution compared to other options such as **Oracle**. 

_Project Structure_

For FinCore's project structure, I have chosen to use a standard build tool, instead of using _Maven_ or _Gradle_. This is because I experienced issues using these build tools in project setup. Here is the file structure that I have at the beginning of my project

<img width="365" height="190" alt="image" src="https://github.com/user-attachments/assets/8e1755bf-55f4-47d7-9f3e-a633fe06faef" />

How to reproduce the same environment as FinCore?

Prerequisites:
- Install `Visual Studio Code`
- Install `OpenJDK Java 21`
- Install `Git`

In Visual Studio Code, Install the following extensions if not pre-installed:
-  `Test Runner for Java` (Microsoft)
-  `Project Manager for Java` (Microsoft)
-  `Extension Pack for Java` (Microsoft)
-  `Debugger for Java` (Microsoft)

From there, complete the following:
- On the Visual Studio Code home page, press `Ctrl + Shift + P`
- Type `Create Java Project`
- Press `Enter`
- Select `No build tools`
- Choose the folder location
- Input a name to the project
- Press `Enter`
- Your new project should appear in another tab + File Tree

Or clone the repository here:
- Open `Visual Studio Code`
- Press `Ctrl + Shift + '` to open a terminal environment
- Copy and Paste this into the terminal `git clone git@github.com:JoshMultiverse/FinCore.git`
- The project should load into `Visual Studio Code`

Frequently Asked Questions (FAQ):

Why wont Visual Studio Code pick up my JDK?

Check your environment variables 

- Press `Windows`
- Type `View advanced system settings`
- Press `Environment Variables`
- Verify there are variables called `PATH` and `JAVA_HOME`
- If they do not exist configure the `PATH` to the path where the JDK was installed.
- Configure/Create `JAVA_HOME` and select the path to the `.bin` file within the Java Project

<<<<<<< HEAD
FinCore Main Menu:

For the menu, I have included 4 different options that the user can take, These are listed in the bullet points below:
=======
FinCore Main Menu 
For the menu, I have included 4 different options that the user can take, These are listed in  the bullet points below:
>>>>>>> 9101a26453ed4ac55563ecf00cc2f9bc8ddb3b45

Deposit

Withdraw

Check Balance

Exit

<<<<<<< HEAD
What it looks like

This is what the application looks like in the console. As you can see, it clearly shows the four options and it's corresponding number. Then, below you can see the prompt that the user has to enter to allow them to access their intended page. I will discuss each page as well as the code behind it in later sections.

Screenshot 2025-10-09 114537
Main Menu

Screenshot 2025-10-09 124434
The code for the main menu is quite simple. I first open a Scanner on line 23, the Java Class for allowing the user to enter text into the console.

I have also set a global variable called isExit whose value is defaulted to false. This allows me to use a while loop to check if this value has been changed, which we will see later in this section. I then print the menu (Shown above). I have set this as a variable because this will be used multiple times throughout the program (S1). You can see the variable definitions below.

Screenshot 2025-10-09 124819
Context: using “”" means that you can print statements across multiple lines, \n allows me to start on a new line to allow for spacing.

What happens if the user enters a letter?

I bet you are wondering what happens if the user enters something that is not a number, for example a letter or a symbol. I have accounted for this type of input as well as others which I will talk about in another section.

If the user does happen to enter a letter, either on purpose or accidentally, in my code I have included areas which catch what would be an error and handle it gracefully. Here is a code snippet to demonstrate what I am describing further.

Screenshot 2025-10-09 115434
As you can see here, I have used a try/except statement to handle any errors that will occur. In this case, I found that the error which occurs when a user enters a String when the program expected an Integer is the InputMismatchException.

Within the try block, the userChoice variable is saved to the input of the user. This will then result in the checkInputIsInRange method is called (see below).

If the user does enter a wrong input, the scanner.nextLine method takes the next line so that an error does not get sent. Then, I print an error message and then the menu again.

Line 105 then calls the same method again using recursion.

Below I will explain the next method that is going to get called (checkInputIsInRange).

What happens if the user enters a number that is outside the range (1-4).

Now we know what happens when the user enters a letter, lets dive deeper into what happens when the user enters a number, but it is out of the range of the menu. Below is a code snippet which checks if their input is outside the current range given.

Screenshot 2025-10-09 122358
As you can see, this method returns a boolean value, as this will get returned to the main method, which then goes and calls upon other methods after it knows the input will not cause an error.

Inside the while loop, this checks if the user's input is less than 1 OR greater than 4. If it is, then an error message gets printed to the console as well as the menu also getting printed. The userChoice variable is then reset to the next integer entered. In the next section, we will explore the process that happens when the user enters a valid choice.

The setBankOperations Method

Once the code has verified that a valid input has been entered, It will move onto the next stage of the code - Calling the correct method. This method is like the delivery driver in a post office. The parcel has been assigned a location, now it just needs to be delivered there. This example can be seen in the code snippet below:

Screenshot 2025-10-09 123517
As you can see in this method, I have decided to use a switch here. This because when there is more than 3 more possible options that it can be, it is a lot more maintainable to use a switch than something like an elif statement for example (S1).

Each case in the switch refers to every possible input that the user could have entered. For cases 1-3, I have linked each case to a method which will call each of the respective methods, which will perform the action set out in the menu. Case 4 is slightly different. It thanks the user for using the service and sets a variable called isExit to true. This is to break out of the while loop which we saw at the beginning of this section.
=======


What it looks like

This is what the application looks like in the console. As you can see, it clearly shows the four options and it's corresponding number. Then, below you can see the prompt that the user has to enter to allow them to access their intended page. I will discuss each page as well as the code behind it in later sections. 

<img width="435" height="147" alt="Screenshot 2025-10-09 114537" src="https://github.com/user-attachments/assets/706e16b8-468d-4fa9-9494-3952ec13dd8c" />



Main Menu


<img width="729" height="332" alt="Screenshot 2025-10-09 124434" src="https://github.com/user-attachments/assets/72126d69-f395-4cd5-8d70-306d38c4d2fb" />


The code for the main menu is quite simple. I first open a `Scanner` on line 23, the Java Class for allowing the user to enter text into the console. 

I have also set a global variable called isExit whose value is defaulted to false. This allows me to use a while loop to check if this value has been changed, which we will see later in this section. I then print the menu (Shown above). I have set this as a variable because this will be used multiple times throughout the program (S1). You can see the variable definitions below. 

<img width="788" height="192" alt="Screenshot 2025-10-09 124819" src="https://github.com/user-attachments/assets/45d66e72-d6bb-4f8d-94ac-4dee74e438d8" />


Context: using `“”"` means that you can print statements across multiple lines, `\n` allows me to start on a new line to allow for spacing. 



What happens if the user enters a letter?

I bet you are wondering what happens if the user enters something that is not a number, for example a letter or a symbol. I have accounted for this type of input as well as others which I will talk about in another section. 

If the user does happen to enter a letter, either on purpose or accidentally, in my code I have included areas which catch what would be an error and handle it gracefully. Here is a code snippet to demonstrate what I am describing further. 

<img width="902" height="346" alt="Screenshot 2025-10-09 115434" src="https://github.com/user-attachments/assets/62c8ec89-de5f-4286-8df2-166265cbd5b6" />


As you can see here, I have used a `try/except` statement to handle any errors that will occur. In this case, I found that the error which occurs when a user enters a `String` when the program expected an `Integer` is the `InputMismatchException`.  

Within the try block, the `userChoice` variable is saved to the input of the user. This will then result in the `checkInputIsInRange` method is called (see below). 

If the user does enter a wrong input, the `scanner.nextLine` method takes the next line so that an error does not get sent. Then, I print an error message and then the menu again. 

Line 105 then calls the same method again using recursion.

Below I will explain the next method that is going to get called (`checkInputIsInRange`). 



What happens if the user enters a number that is outside the range (1-4). 

Now we know what happens when the user enters a letter, lets dive deeper into what happens when the user enters a number, but it is out of the range of the menu. Below is a code snippet which checks if their input is outside the current range given.  

<img width="1001" height="217" alt="Screenshot 2025-10-09 122358" src="https://github.com/user-attachments/assets/9974475f-dfb1-4a3d-b506-a1bac509f914" />


As you can see, this method returns a `boolean` value, as this will get returned to the main method, which then goes and calls upon other methods after it knows the input will not cause an error. 

Inside the `while` loop, this checks if the user's input is less than 1 `OR` greater than 4. If it is, then an error message gets printed to the console as well as the menu also getting printed. The `userChoice` variable is then reset to the next `integer` entered. In the next section, we will explore the process that happens when the user enters a valid choice. 



The `setBankOperations` Method

Once the code has verified that a valid input has been entered, It will move onto the next stage of the code - Calling the correct method. This method is like the delivery driver in a post office. The parcel has been assigned a location, now it just needs to be delivered there. This example can be seen in the code snippet below:

<img width="896" height="446" alt="Screenshot 2025-10-09 123517" src="https://github.com/user-attachments/assets/5739bb5f-5bb6-49b6-94ee-ab2372924f7f" />

As you can see in this method, I have decided to use a `switch` here. This because when there is more than 3 more possible options that it can be, it is a lot more maintainable to use a `switch` than something like an `elif` statement for example (S1). 

Each case in the `switch` refers to every possible input that the user could have entered. For cases 1-3, I have linked each case to a method which will call each of the respective methods, which will perform the action set out in the menu. Case 4 is slightly different. It thanks the user for using the service and sets a variable called `isExit` to `true`. This is to break out of the `while` loop which we saw at the beginning of this section. 


>>>>>>> 9101a26453ed4ac55563ecf00cc2f9bc8ddb3b45

Deposit and Withdraw Methods

I have decided to include these methods in the same section as they're incredibly similar in their overall structure. They can be split into many individual methods which are:

<<<<<<< HEAD
display[ACTION_NAME]Text

truncateTo2DP

calculateNewBalance

printNewBalance

In this section, I will explore all of these sub methods and what they contribute towards the overall method.

display[ACTION_NAME]Text

This submethod is the entry method which will get called when the overall method is called from the main program. This can be viewed like the first domino in a chain. It will slowly begin a domino effect of calling all the other helper methods which enable this action to be possible. Lets dive into some code!

Screenshot 2025-10-09 130101
This method begins by prompting the user to get how much they would like to withdraw from their current balance. Similar to the previous methods I discussed on the last section, this has a similar structure to it.

As you can see, I have again used a try/except statement to handle any unexpected inputs which may throw an error. Because I am passing the Scanner through as a parameter, this means that I do not need to re open another Scanner. Therefore, I can access the next input directly.

See how I am not using nextInt() this time? This is because money can be up to two decimal places. This means as a developer, I have to account for this edge case. As a result, I have used nextDouble() instead of nextInt().

In the while loop, I am checking that the value the user has entered is less than 0. This is another edge case. This is because I do not want user's performing the opposite action to what they intend to do.

Context: Withdrawing $-1 from a $1 account will cause 2 because 1--1=2.

If this is the case, then I print an error message to the console and call the method recursively again. If they did not enter a minus value, then the method will call the next method down in the list. In this case, it is the calculateNewBalance method. When this method gets called, I am passing two parameters to it:

currentBalance

amountToWithdraw

Within the amountToWithdraw, I wrapped the truncateTo2DP method inside it. This method is responsible for ensuring that the input is in the most accurate form possible, while still being valid. This method can be seen below:

Screenshot 2025-10-09 140429
Context: Math.floor() rounds down positive numbers

Calculate New Balance + Print New Balance

This method is the only place where there is a significant difference between the deposit and the withdraw method. This is because this is the method where the calculation is performed to either add/remove money to/from the current balance stored. This method can be seen below.

Screenshot 2025-10-09 141330
As you can see above, the currentBalance and the amountToDeposit are both taken in as parameters as these are the two operands (the numbers which the operation will be performed on) required for this calculation. The two numbers added together (deposit)/taken away(withdraw) are then passed as a parameter to the final method printNewBalance, which can be seen below:

Screenshot 2025-10-09 141718
In this method, the newBalance parameter which is the result of the calculation is then concatenated to the print statement, to indicate the result of the calculation to the user. The newBalance parameter is then saved to the balanceAfterTransaction, which allows for further processing.

In this section, we discussed the structure of the deposit + withdraw methods and the overall role that they play in the program flow. In the next section, we will explore the structure of the CheckBalance method.
=======
`display[ACTION_NAME]Text`

`truncateTo2DP`

`calculateNewBalance`

`printNewBalance`

In this section, I will explore all of these sub methods and what they contribute towards the overall method. 



`display[ACTION_NAME]Text`

This submethod is the entry method which will get called when the overall method is called from the main program. This can be viewed like the first domino in a chain. It will slowly begin a domino effect of calling all the other helper methods which enable this action to be possible. Lets dive into some code!

<img width="948" height="498" alt="Screenshot 2025-10-09 130101" src="https://github.com/user-attachments/assets/a67f8d53-2827-4d15-86a0-02dcbe9f32e0" />


This method begins by prompting the user to get how much they would like to withdraw from their current balance. Similar to the previous methods I discussed on the last section, this has a similar structure to it.

As you can see, I have again used a `try/except` statement to handle any unexpected inputs which may throw an error. Because I am passing the `Scanner` through as a parameter, this means that I do not need to re open another `Scanner`. Therefore, I can access the next input directly. 

See how I am not using `nextInt()` this time? This is because money can be up to two decimal places. This means as a developer, I have to account for this edge case. As a result, I have used `nextDouble()` instead of `nextInt()`. 

In the `while` loop, I am checking that the value the user has entered is `less than 0`. This is another **edge case.** This is because I do not want user's performing the opposite action to what they intend to do. 



Context: Withdrawing `$-1` from a `$1` account will cause `2` because `1--1=2`. 



If this is the case, then I print an error message to the console and call the method recursively again. If they did not enter a minus value, then the method will call the next method down in the list. In this case, it is the `calculateNewBalance` method. When this method gets called, I am passing two parameters to it:

`currentBalance`

`amountToWithdraw`

Within the `amountToWithdraw,` I wrapped the `truncateTo2DP` method inside it. This method is responsible for ensuring that the input is in the most accurate form possible, while still being valid. This method can be seen below:

<img width="692" height="81" alt="Screenshot 2025-10-09 140429" src="https://github.com/user-attachments/assets/25ff2e4d-abd5-40a5-bb59-95727ebaf99d" />

Context: `Math.floor()` rounds down positive numbers



Calculate New Balance + Print New Balance

This method is the only place where there is a significant difference between the deposit and the withdraw method. This is because this is the method where the calculation is performed to either add/remove money to/from the current balance stored. This method can be seen below. 

<img width="884" height="90" alt="Screenshot 2025-10-09 141330" src="https://github.com/user-attachments/assets/0dbfa92b-f14c-43b0-a699-7b0615a55551" />

As you can see above, the `currentBalance` and the `amountToDeposit` are both taken in as parameters as these are the two **operands** (the numbers which the operation will be performed on) required for this calculation. The two numbers added together (deposit)/taken away(withdraw) are then passed as a parameter to the final method printNewBalance, which can be seen below:

<img width="690" height="104" alt="Screenshot 2025-10-09 141718" src="https://github.com/user-attachments/assets/01937c6d-a1f5-4a98-82a1-a0bd51a1c17a" />

In this method, the `newBalance` parameter which is the result of the calculation is then concatenated to the print statement, to indicate the result of the calculation to the user. The `newBalance` parameter is then saved to the `balanceAfterTransaction`, which allows for further processing. 



In this section, we discussed the structure of the deposit + withdraw methods and the overall role that they play in the program flow. In the next section, we will explore the structure of the `CheckBalance` method. 

>>>>>>> 9101a26453ed4ac55563ecf00cc2f9bc8ddb3b45

The Check Balance Method

Within this method, it is only responsible for taking in the current balance that the user has and outputting it to the console. Therefore, it is a lot more simpler than the previous two methods, which both require some form of calculation to be completed. This method is further explained below:

<<<<<<< HEAD
Screenshot 2025-10-09 142104
As you can see, the currentBalance variable is passed in as a parameter and is once again concatenated to the end of a print statement - enhancing user experience.
=======
<img width="664" height="82" alt="Screenshot 2025-10-09 142104" src="https://github.com/user-attachments/assets/2d90925d-9f37-48b5-b39b-167691132ab9" />

As you can see, the `currentBalance` variable is passed in as a parameter and is once again concatenated to the end of a print statement - enhancing user experience. 







>>>>>>> 9101a26453ed4ac55563ecf00cc2f9bc8ddb3b45




