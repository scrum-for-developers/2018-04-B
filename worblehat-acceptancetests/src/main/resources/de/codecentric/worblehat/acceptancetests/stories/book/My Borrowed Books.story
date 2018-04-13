Meta:
@themes Book

Narrative:
As a user
I want to see all my borrowed books
So that I know when to return them

Scenario: A borrowed book appears in the 'My borrowed books' list
Given a library, containing a book with isbn <isbn>
When user <borrower> borrows the book <isbn>
Then the book list for user <borrower> contains only one book with isbn <isbn> and return date 14 days from today

Examples:

| isbn       | borrower      |
| 0552131075 | user@test.com |
