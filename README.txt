*******************************************************************
"geektrust  problem 1: meet the family"
*******************************************************************

This is a java based application. 
Tech stack is
	Java 1.8, Gradle 5.0 for building, Junit 4+ for unit tests

Unit tests are not exhaustive but they are indicative of the behavior of the problem statement viz. 
to get brothers of a family member.

Please follow below steps to run the app:

1. Download the latest java 1.8 from here:
https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

2. Make sure JAVA_HOME is set to the JDK installed folder & PATH has  %JAVA_HOME%/bin path.

3. Try to execute that java -version to check java installed properly.

4. Now unzip the geektrust.zip folder which will be extracted to "geektrust" folder.

5. Open a command line and navigate to "geektrust" folder.

6. Run gradlew (for *nix based) / gradlew.bat( for windows )  
	For E.g.  on windows execute -->  "gradlew.bat build"

7. It will download the necessary gradle 5.0 software, installs, and builds the project with a SUCCESS message.

8. Run the app using the command
	if you have not changed the build.gradle file, then the build folder will be generated inside "geektrust"
	extract folder after running above steps .
	Run from geektrust folder the following command:
	    java -jar build/libs/geektrust.jar /path/to/your_input.txt

NOTE:
  1. King Shan would be added as addKing operation as a special operation just for him alone as he is the root.
   However the spouse is not yet set because we have not yet read the next line from the familytree.txt seed.
   This is the reason when we read the Queen Anga from the next line for ADD_SPOUSE operation, we retrieve the
   King Shan and set the spouse as Queen Anga. Hence it is pertinent that setSpouse method exist in Person object.
   Feel free to comment.
  2. All the tests for all required relationships as per the problem statement are covered.


ASSUMPTIONS:
1. A member of a family can have at most 1 spouse  ( 1 life partner)
2. All members of the family tree are assumed to be heterosexuals.
3. A child to a family member cannot be added until the family member has a spouse ( i.e. if a person is bachelor/spinster we cannot add a child)
4. A family member will have only 1 set of parents ( a father and a mother) , we are not considering cases for "step mom/dad" here.
   Hence father and mother are encapsulated inside "Parents" class which in turn is encapsulated inside the "Person" class.
5. Each relation is considered a different behavior and for each behavior we need to implement its logic.
   For e.g.
        The logic for retrieving Maternal Aunts of a person would be as follows:
            Get the persons mother from family tree
            Get all the sisters of the mother
            Return the sisters who are the maternal aunts of the person

         The logic for retrieving Paternal Uncles of a person would be follows:
            Get the person's father
            Get all the brothers of the father
            Return all the brothers from the second step above as they constitute paternal uncles of the person.
6. Two family members do not have same full name.

		
		 			

