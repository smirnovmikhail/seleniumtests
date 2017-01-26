
Scenario: A scenario to check link on the site

Scenario: monitor
Meta:  @monitor


When I open site <url>
When I find all links on the page
Then I shall be happy

Examples:
|url|status|
|https://www.google.com.ua/|OFF|




