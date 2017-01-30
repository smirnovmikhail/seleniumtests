
Scenario: A scenario to check link on the site

Scenario: monitor
Meta:  @monitor


When I open site <url>
When I find all links on the site with <depth>
When Show statistic
Then I shall be happy

Examples:
|url|depth|
|https://google.com.ua|10|




