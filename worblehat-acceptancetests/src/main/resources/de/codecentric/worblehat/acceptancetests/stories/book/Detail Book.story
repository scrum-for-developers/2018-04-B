Meta:
@themes Book

Narrative:
In order to see details of a book I want to open a detail page of a book

Scenario:

Given a library, containing a book with isbn <isbn> and title <title>
When user opens the details of the book with title <title>
Then only one book is shown

Examples:

| isbn       | title        |
| 0552131075 | A book title |
| 0552131074 | Testbuch2    |

