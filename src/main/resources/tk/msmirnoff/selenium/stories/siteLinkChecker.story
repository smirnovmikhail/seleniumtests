
Scenario: A scenario to check link on the site

Scenario: monitor
Meta:  @monitor


When I open site <url>
!--When I sleep <sec> sec
!--May be useful for sites under cloudflare ddos protection
When I find all links on the site with <depth>
When Show statistic
Then I shall be happy

Examples:
|url|depth|sec
|http://test.msmirnoff.tk/|7|10|




