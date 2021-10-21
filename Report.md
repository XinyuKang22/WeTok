# [WeTok] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

*Here are some tips to write a good report:*

* *Try to summarise and list the `bullet points` of your project as much as possible rather than give long, tedious paragraphs that mix up everything together.*

* *Try to create `diagrams` instead of text descriptions, which are more straightforward and explanatory.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report.*

*Please remove the instructions or examples in `italic` in your final report.*

## Table of Contents

- [[WeTok] Report](#we-report)
  - [Table of Contents](#table-of-contents)
  - [Team Members and Roles](#team-members-and-roles)
  - [Conflict Resolution Protocol](#conflict-resolution-protocol)
  - [Application Description](#application-description)
    - [Application Use Cases and or Examples](#application-use-cases-and-or-examples)
  - [Application UML](#application-uml)
  - [Application Design and Decisions](#application-design-and-decisions)
    - [Data Structures](#data-structures)
      - [AVL Tree](#avl-tree)
    - [Design Patterns](#design-patterns)
      - [Singleton](#singleton)
      - [Template](#template)
      - [DAO](#dao)
    - [Grammars](#grammars)
    - [Surpise Item](#surpise-item)
      - [(i) Ranking algorithm](#ranking-algorithm)
      - [(ii) Simple personalisation](#simple-personalisation)
  - [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
  - [Testing Summary](#testing-summary)
    - [AVL Tree Testings](#avl-tree-testings)
    - [Dao and Bean Testings](#dao-and-bean-testings)
    - [Parser and Tokenizer Tests](#tokenizer-and-parser-testings)
    - [Ranking Tests](#ranking-testings)
  - [Implemented Features](#implemented-features)
  - [Team Meetings](#team-meetings)

## **Team Members and Roles**

| UID | Name | Role |
| :--- | :----: | :--- |
| u6120911 | Xinyu Kang | Integration engineer, Data structure designer, Ranking algorithm designer |
| u7151386 | Xinyue Hu | Database Designer, Data structure designer, Integration engineer |
| u6684233 | Yuxin Hong | Tokenizer and parser designer and tester, Ranking algorithm tester |
| u6111569 | Zhaoting Jiang | UI Designer, Fragment designer |

## **Conflict Resolution Protocol**

1. Conflictors organize their views, list the pros and cons of their choice.
2. Initiate a zoom meeting with all team member.
3. Conflictors presenting the confliction and why disagree with the others.
4. Team member do a vote and share the reason.
5. As a result, merge conflictors's solutions or take more agreed solutions.

## **Application Description**

*Wetok is a social media app specifically aimed at person with a strong personality. You can share your status and mood with your friends, people in the same city, and even strangers anytime, anywhere. In here, you do not have to worry about cyber-violence and personal abuse, we only provide you with a pure sharing platform. As our logo shows, we talk, but we don't comment. Furthermore, you can search for tags you're interested in and subscribe people that you're interested in. You can understand what is happening in this world through wetok. Welcome to Wetok.*

### **Application Use Cases and or Examples**

*[Provide use cases and examples of people using your application. Who are the target users of your application? How do the users use your application?]*

*Here is a pet training application example*

*Molly wants to inquiry about her cat, McPurr's recent troublesome behaviour*
1. *Molly notices that McPurr has been hostile since...*
2. *She makes a post about... with the tag...*
3. *Lachlan, a vet, writes a reply to Molly's post...*
4. ...
5. *Molly gives Lachlan's reply a 'tick' response*

*Here is a map navigation application example*

*Targets Users: Drivers*

* *Users can use it to navigate in order to reach the destinations.*
* *Users can learn the traffic conditions*
* ...

*Target Users: Those who want to find some good restaurants*

* *Users can find nearby restaurants and the application can give recommendations*
* ...

*List all the use cases in text descriptions or create use case diagrams. Please refer to https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-use-case-diagram/ for use case diagram.*

## **Application UML**

![ClassDiagramExample](./images/ClassDiagramExample.png)
*[Replace the above with a class diagram. You can look at how we have linked an image here as an example of how you can do it too.]*

## **Application Design and Decisions**

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design. Here is an example for the subsection `Data Structures`:*

*I used the following data structures in my project:*

1. *LinkedList*

   * *Objective: It is used for storing xxxx for xxx feature.*

   * *Locations: line xxx in XXX.java, ..., etc.*

   * *Reasons:*

     * *It is more efficient than Arraylist for insertion with a time complexity O(1)*

     * *We don't need to access the item by index for this feature*

2. ...

3. ...

### **Data Structures**

##### **AVL Tree**

We selected AVL tree as the data structure of posts operations. AVL tree is a self-balancing binary search tree. It controls the height of the tree and prevents it from becoming skewed. For operations considered they have time complexity:
- Rotations to achieve balanced tree *O(1)*
- Insertion *O(log(n))*
- Deletion *O(log(n))*
- Search *O(log(n))*
- Max/Min *O(log(n))*

It is an efficient data structure and here is the UML of our implementation:<br />
   ![AVLDiagram](./images/AVLDiagram.png)<br />

### **Design Patterns**

We used three design patterns: Singleton, Template, DAO
##### **Singleton**
   * Location: *CurrentUser.java*
   * We used Singleton design pattern in CurrentUser to make sure there must be exactly one instance of the current user.<br /><br />
   ![SingletonDiagram](./images/SingletonDiagram.png)<br />

##### **Template**
   *  Location: *ScoreTemplate.java* (abstract class), *RelevanceScore.java* (concrete class), *ImportanceScore.java* (concrete class), *UserSimilarity.java* (concrete class)
   *  We want to sort the posts based on three criteria. There is a common process (i.e., calculate scores, normalize scores) to scoring the posts but each criterion has its own scoring logic. 
   *  So, we used a score template to define the skeleton of the scoring algotirthm, and let subclasses redefine certain steps of the algotirthm without changing the main structure.<br /><br />
   ![TemplateDiagram](./images/TemplateDiagram.png)<br />

##### **DAO**
   * Location: *UserDao.java*, *PostDao.java*
   * We want to decouple domain logic from persistence mechanisms and avoid exposing details of the data storage. The two DAO classes provided convenient access to user data in whole project. <br /><br />
   ![DaoDiagram](./images/DaoDiagram.png)<br />

### **Grammars**

<br> *Production Rules* <br>
\<exp> ::= \<term> | \<term> '|' \<exp> 

\<term> ::= \<factor> | \<factor> '&' \<term> 

\<factor> ::= \<tag> | '(' \<exp> ')' 

According to this gramma, we can parse *AND* and *OR* operations. For example, suppose we have the expression **condition1 & condition2 | condition3**, human will process it as **(condition1 & condition2) | condition3**, so does our gramma will do. Another example is **condition1 & (condition2 | condition3 & condition4)**, human will process it as **condition1 & (condition2 | (condition3 & condition4))**, so does our gramma will do. In application, the advantages of our gramma is to filter tag of post with *AND* and *OR* operation. It takes tag as its condition and only return post that satified given condition in expression.


### **Tokenizer and Parsers**
Generally speaking, the advantages of our design is we to search multiple tags at once and this is also the design approach of our gramma. Operations *AND* and *OR* can be aplied on multiple tags search, that is, the intersection and union of single search result. By search *#tag1&#tag2*, the intersection of individual search result of *#tag1* and *#tag2* will be returned. By search *#tag1|#tag2*, the union of of individual search result of *#tag1* and *#tag2* will be returned. And of course the operators can be freely used to create more expressions. By default, the precedence of *AND* operation is greater then *OR* operation. And precedence can be changed parentheses *LBRA* and *RBRA*.

### **Surpise Item**
##### **Ranking algorithm**
Based on the *Pariser talk*, we decided to sort the posts by three criteria: relecvance; importance; user similarity.<br />
* **Relevance**<br />
  The class *RelevanceScore* evaluates the relecance of the query and the retrieved posts by calculating tf-idf scores. The score of post d given query q = (t_1, t_2, ..., t_m) is: <br />
  ![RelevanceFormula](./images/RelevanceFormula.png)<br />
  We expect the posts containing more "rare words" that appeared in the query to be more relevant. The rare word here refers to the word that does not exist in many posts. <br />
 
* **Importance**<br />
  The class *ImportanceScore* evaluates the importance of the retrieved posts by calculating the post time, post likes, and the user's followers:<br />
  ![ImportanceFormula](./images/ImportanceFormula.png)<br />
  We expect the posts with more likes, the posts sent by users with more followers, and the latest posts to be more important to society, and thus have higher ranking scores. <br />
 
* **User Similarity**<br />
  The class *UserSimilarityScore* evaluates the similarity of the current user and the senders of the posts by evaluating their subscribers, posts, and addresses:<br />
  ![SimilarityFormula](./images/SimilarityFormula.png)  <br />
  The post sent by the user who is considered as more 'similar' with the current user will have a lower ranking score.We expect to use this algotithm to offset some negative effects of the *'Filter Bubbles'*.  <br />
 
* **Overall Score**<br />
  
  ![OverallFormula](./images/OverallFormula.png)<br />
  All of the weights introduced in this algorithm were intuitively chose by our group members. In practice we should use machine learning techniques to train the best weights.<br />
  We expect that the posts having strong relevance with the query, the posts which are important to society, and the posts sent by the users who are "different" with the user, to have better ranking in the search results presenting.<br />
  More and more intelligent recommendation systems create the *'Filter Bubbles'* for users. We intend to "break" the bubble by implementing the ranking algorithm from users similarity dimension. The score of the posts created by the users who are very "similar" with you will be scaled down. People will have more chances to receive different viewpoints from social media. <br />

##### **Simple personalisation**
*In order to create unique personalization features, we recorded the location of the user while recording the user login data. Based on the user's location, we added a search function, which is the city search function. The local search function searches for all posts in the same city based on the current user's location. This will make it easier for users to see what's going on around them.*


## **Summary of Known Errors and Bugs**

*[Where are the known errors and bugs? What consequences might they lead to?]*

*Here is an example:*

1. *Bug 1:*

- *A space bar (' ') in the sign in email will crash the application.*
- ... 

2. *Bug 2:*
3. ...

*List all the known errors and bugs here. If we find bugs/errors that your team do not know of, it shows that your testing is not through.*

## **Testing Summary**

### **AVL Tree Testings**
Number of test cases: 13 <br />
  ![AVLTest](./images/AVLTest.png)  <br />

Code coverage: <br />
  ![AVLTest2](./images/AVLTest2.png)  <br />

Types of tests created: 
1. *sameKeyInsertTest* : test whether the AVL tree can insert multiple values into one nodes
2. *insertInOrderTest* : test whether the AVL tree can correctly insert nodes in order
3. *leftRotateTest* : test whether the AVL tree can correctly left rotate
4. *rightRotateTest* : test whether the AVL tree can correctly right rotate
5. *balanceFactorTest* : test whether the class can generate correct balance factors
6. *advancedRotationsTest* :  test whether the AVL tree can handle complex rotations
7. *deleteNotExistException* : test whether the AVL tree can throw an exception when deleting a node that not exists
8. *deleteLeafTest* : test whether the AVL tree can delete a leaf node
9. *deleteSingleChildNodeTest* : test whether the AVL tree can correctly delete a node with one child
10. *deleteTwoChildrenNodeTest* : test whether the AVL tree can correctly delete a node with two children 
11. *deleteRootTest* : test whether the AVL tree can correctly delete the root node 
12. *searchByKeyTest* : test whether the AVL tree can correctly find the node based on the key 
13. *searchByNotExistKeyTest* : test whether the AVL tree returns null when searching a key that not exists 

### **Dao and Bean Testings**
Number of test cases: 14 <br />
  ![DaoAndBeanTest1](./images/DaoBeanTest.png)  <br />

Code coverage: <br />
  ![DaoAndBeanTest2](./images/DaoBeanTestCoverage.png)  <br />

Tests created for Bean:
1. *userEmptyTest* : test whether all the setter and getter in User class are valid.
2. *constructorTest* : test whether the constructor in User class is valid.

Tests created for Dao:
1. *getTagListTest* : test whether the PostDao can get all the tags in posts.
2. *findInsertIndexTest* : test whether the PostDao can find the right place to insert post.
3. *addPostTest* : test whether the PostDao can add a post to database.
4. *getPostsTest* : test whether the PostDao can get all the posts in database.
5. *findUserByEmailTest* : test whether the UserDao can find the right user by it's email.
6. *findUserByIdTest* : test whether the UserDao can find the right user by it's user id.
7. *setFriendsTest* : test whether the UserDao can set friends for corresponding user.
8. *setFollowersTest* : test whether the UserDao can set followers for corresponding user.
9. *setSubscribersTest* test whether the UserDao can set subscribers for corresponding user.
10. *getPostsTest* : test whether the UserDao can get all the posts of each user.
11. *setPostInfoTest* : test whether the UserDao can instantiate each user's post.
12. *addUserTest* : test whether the UserDao can add user to database.

### **Parser and Tokenizer Tests**
Number of test cases: 12 <br />
  ![ParserAndTokenizerTest1](./images/PTTest1.png)  <br />

Code coverage: <br />
  ![ParserAndTokenizerTest2](./images/PTTest2.png)  <br />

Tests created for Tokenizer: 
1. *testAndToken* : test whether *AND* token can be recogize.
2. *testOrToken* : test whether *OR* token can be recogize.
3. *testFirstToken* : test whether first *LBRA* token can be recogize.
4. *testMidTokenResult* : test whether *TAG* and *OR* token at middle can be recogize.
5. *testAdvancedTokenResult* : test whether tokens from *(#weekend | #mood) & #time* can be tokenized.
6. *testExceptionToken* :  test whether tokenizer throw exceptions as expected.

Tests created for Parser: 
1. *testSingleTag* : test whether single-tag-search is correct.
2. *testSimpleAnd* : test whether search tag with *AND* operation is correct.
3. *testSimpleOr* : test whether search tag with *OR* operation is correct.
4. *testSimpleCase* : test whether search tag with multiple *AND* operations is correct.
5. *testMidCase* : test whether search tag with *AND* and *OR* operations is correct.
6. *testIllegalProductionException* :  test whether parser throw exceptions as expected.

### **Ranking Tests**
Number of test cases: 8 <br />
  ![RankingTest1](./images/RankingTest1.png)  <br />

Code coverage: <br />
  ![RankingTest2](./images/RankingTest2.png)  <br />

Tests created for Ranking Tests: 
1. ImportanceScoreTest: 3 tests for 3 component of calculating importance score: time, like, follower.
2. RelevanceScoreTest: 2 tests for 1 tag relevent and 2 tags relevant.
3. UserSimilarityScoreTest: 3 tests for 3 component of calculating user similarity score: location, subscriber, post.


## Implemented Features

*Improved Search*

1. *Search functionality can handle partially valid and invalid search queries. (medium)*

*UI Design and Testing*

1. *UI must have portrait and landscape layout variants as well as support for different screen sizes. Simply using Android studio's automated support for orientation and screen sizes and or creating support without effort to make them look reasonable will net you zero marks. (easy)*

*Greater Data Usage, Handling and Sophistication*

1. *User profile activity containing a media file (image, animation (e.g. gif), video). (easy)*
2. *Deletion method of either a Red-Black Tree and or AVL tree data structure. The deletion of nodes must serve a purpose within your application (e.g. deleting posts). (hard)*

*User Interactivity*

1. *The ability to micro-interact with 'posts' (e.g. like, report, etc.) [stored in-memory]. (easy)*
2. *The ability for users to ‘follow’ other users. There must be an adjustment to either the user’s timeline in relation to their following users or a section specifically dedicated to posts by followed users. [stored in-memory] (medium)*

*User Interactivity*

1. *Use Firebase to implement user Authentication/Authorisation. (easy)*

## Team Meetings

- *[Team Meeting 1](./Meeting1.md)*
- *[Team Meeting 2](./Meeting2.md)*
- *[Team Meeting 3](./Meeting3.md)*
- *[Team Meeting 4](./Meeting4.md)*