# PriceChecker

The price checker compare tool gets the current price for a product. It'll fetch, parse and store only updated prices therefore not clutering up the database with duplicate entries.

This tool makes my life a little easier. If I want to monitor the price of a game, I can simply add it to the website to notify me on the price, to help me get the best deals on products.

This will email daily (maybe in the future will email when there is a change in price for a product within the system). First it will fetch all products that are active, I can enable or disable a product within the admin section (note: there is no point in the admin window being secure if there it's no accessible to the outside world, subject to change). Then it'll email me out a report of all the products within the applicaiton. Simple.

## Like to haves:

* Email only when there is a price difference
* Secure the backend admin section with a login
* A page within the admin section that shows the price differences, nice graphs etc