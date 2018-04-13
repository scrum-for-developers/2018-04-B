Meta:
@themes Book

Narrative:
In order to give back borrowed books
As a borrower
I want to return a single borrowed book

Scenario:
Given an empty library
And borrower <borrower> has borrowed books <isbns1>
When borrower <borrower> returns the book with isbn <returnisbn>
Then the book <returnisbn> is not borrowed anymore by borrower <borrower>


Examples:    
    
| borrower        | isbns1                | returnisbn  |
| user1@dings.com | 0321293533 1234567962 | 1234567962  |