# About Paymentwall
[Paymentwall](https://www.paymentwall.com/?source=gh) is the leading global payments platform.

Merchants can plugin Paymentwall's API to accept payments from over 150 different methods including credit cards, debit cards, bank transfers, SMS/Mobile payments, prepaid cards, eWallets, landline payments and others. 

To sign up for a Paymentwall Merchant Account, [click here](https://www.paymentwall.com/signup/merchant?source=gh).

# Paymentwall Java Library
This library allows developers to use [Paymentwall APIs](https://docs.paymentwall.com/?source=gh) (Digital Goods API, Cart API, Brick API, Virtual Currency API).

To use Paymentwall, all you need to do is to sign up for a Paymentwall Merchant Account so you can setup an Application designed for your site.
To open your merchant account and set up an application, you can [sign up here](https://www.paymentwall.com/signup/merchant?source=gh).

# Installation
To install the library in your environment, you can download the [ZIP archive](https://github.com/paymentwall/paymentwall-java/archive/master.zip), unzip it and place into your project.

Alternatively, you can run:

    git clone git://github.com/paymentwall/paymentwall-java.git

Then use a code samples below.
# Code Samples

## Adding Paymentwall to your project using Apache Maven

    <dependencies>
		<dependency>
			<groupId>com.paymentwall</groupId>
			<artifactId>paymentwall-java</artifactId>
			<version>2.0.3</version>
		</dependency>
    </dependencies>

## Digital Goods API

#### Initializing Paymentwall

    import com.paymentwall.java.*;
	
	Config.getInstance().setLocalApiType(Config.API_GOODS);
	Config.getInstance().setPublicKey("YOUR_APPLICATION_KEY");
	Config.getInstance().setPrivateKey("YOUR_SECRET_KEY");

#### Widget Call

[Checkout API details](https://docs.paymentwall.com/integration/checkout-home)

The widget is a payment page hosted by Paymentwall that embeds the entire payment flow: selecting the payment method, completing the billing details, and providing customer support via the Help section. You can redirect the users to this page or embed it via iframe. Below is an example that renders an iframe with Paymentwall Widget.

    WidgetBuilder widgetBuilder = new WidgetBuilder("user12345", "pw");
	widgetBuilder.setProduct(
		new ProductBuilder("product301") {{
			setAmount(0.99);
			setCurrencyCode("USD");
			setName("100 coins");
			setProductType(Product.TYPE_FIXED);
		}}.build()
	);
	widgetBuilder.setExtraParams(
		new LinkedHashMap<String, String>() {{
			put("email", "user@hostname.com");
		}}
	);
	Widget w = widgetBuilder.build();
	return w.getHtmlCode();

#### Pingback Processing

The Pingback is a webhook notifying about a payment being made. Pingbacks are sent via HTTP/HTTPS to your servers. To process pingbacks use the following code:

    Pingback pingback = new Pingback(request.getParameterMap(), request.getRemoteAddr());
	if (pingback.validate()) {
		String goods = pingback.getProductId();
		String userId = pingback.getUserId();
		if (pingback.isDeliverable()) {
			// deliver Product to user with userId
		} else if (pingback.isCancelable()) {
			// withdraw Product from user with userId
		}
		return "OK";
	} else
		return pingback.getErrorSummary();

## Virtual Currency API

#### Initializing Paymentwall

    import com.paymentwall.java.*;
    
    Config.getInstance().setLocalApiType(Config.API_VC);
    Config.getInstance().setPublicKey("YOUR_APPLICATION_KEY");
    Config.getInstance().setPrivateKey("YOUR_SECRET_KEY");

#### Widget Call

    WidgetBuilder widgetBuilder = new WidgetBuilder("user12345","p1_1");
	widgetBuilder.setExtraParams(
		new LinkedHashMap<String, String>() {{
			put("email", "user@hostname.com");
		}}
	);
	Widget w = widgetBuilder.build();
    return w.getHtmlCode();

#### Pingback Processing

    Pingback pingback = new Pingback(request.getParameterMap(), request.getRemoteAddr());
	if (pingback.validate()) {
		Integer currency = pingback.getVirtualCurrencyAmount();
		String userId = pingback.getUserId();
		if (pingback.isDeliverable()) {
			// deliver Product to user with userId
		} else if (pingback.isCancelable()) {
			// withdraw Product from user with userId
		}
		return "OK";
	} else
		return pingback.getErrorSummary();

## Cart API

#### Initializing Paymentwall

    import com.paymentwall.java.*;
	
    Config.getInstance().setLocalApiType(Config.API_CART);
    Config.getInstance().setPublicKey("YOUR_APPLICATION_KEY");
    Config.getInstance().setPrivateKey("YOUR_SECRET_KEY");


You have to set up your products in merchant area for exact regions first in order to use widget call example code below.

    WidgetBuilder widgetBuilder = new WidgetBuilder("user12345","p1_1");
	widgetBuilder.setExtraParams(
		new LinkedHashMap<String, String>() {{
			put("email", "user@hostname.com");
		}}
	);
	widgetBuilder.setProducts(new ArrayList<Product>(){{
		add(
			new ProductBuilder("product1") {{
				setAmount(9.99);
				setCurrencyCode("USD");
				setName("Product 1");
			}}.build()
		);
		add(
			new ProductBuilder("product2"){{
				setAmount(1);
				setCurrencyCode("USD");
				setName("Product 2");
			}}.build()
		);
	}});
	Widget w = widgetBuilder.build();
    return w.getHtmlCode();

#### Pingback Processing

    Pingback pingback = new Pingback(request.getParameterMap(), request.getRemoteAddr());
	if (pingback.validate()) {
		ArrayList<Product> goods = pingback.getProducts();
		String userId = pingback.getUserId();
		if (pingback.isDeliverable()) {
			// deliver Product to user with userId
		} else if (pingback.isCancelable()) {
			// withdraw Product from user with userId
		}
		return "OK";
	} else
		return pingback.getErrorSummary();

## Brick

#### Initializing Paymentwall

    Config.getInstance().setPublicKey("YOUR_APPLICATION_KEY");
    Config.getInstance().setPrivateKey("YOUR_SECRET_KEY");

#### Create a one-time token

	OneTimeToken token = new OneTimeToken();
	token = (OneTimeToken) token.create(new LinkedHashMap<String, String>(){{
		put("public_key", Config.getInstance().getPublicKey());
		put("card[number]", "4242424242424242");
		put("card[exp_month]", "11");
		put("card[exp_year]", "19");
		put("card[cvv]", "123");
	}});
	return token.getToken();

#### Charge

	Charge charge = new Charge();
	charge = (Charge)charge.create(new HashMap<String, String>(){{
		put("token", token.getToken());
		put("email", "email@example.com");
		put("currency", "USD");
		put("amount", "10.99");
		// if you receive one-time token and make charge on same server, then use 
		// put("fingerprint", request.getParameter("fingerprint"));
		// instead of next 2 lines
		put("browser_domain", request.getHeader("VIA"));
		put("browser_ip", request.getRemoteAddr());
		put("description", "description");
	}});

	JSONObject response = charge.getPublicData();
	if (charge.isSuccessful()) 
		if (charge.isCaptured()) {
			//deliver a product
		} else if (charge.isUnderReview()) {
			//decide on risk charge
		}
	return response.toString(); // need for JS communication

#### Charge - refund

	Charge charge = new Charge("CHARGE_ID");
	charge = (Charge)charge.refund();
	return charge.isRefunded();

#### Subscription

	Subscription subscription = new Subscription();
	subscription = (Subscription) subscription.create( new HashMap<String, String>() {{
		put("token" , token.getToken());
		put("email", "email@example.com");
		put("currency", "USD");
		put("amount", "10.99");
		// if you receive one-time token and make charge on same server, then use 
		// put("fingerprint", request.getParameter("fingerprint"));
		// instead of next 2 lines
		put("browser_domain", request.getHeader("VIA"));
		put("browser_ip", request.getRemoteAddr());
		put("description", "description");
		put("plan", "goodsid");
		put("period", "week");
		put("period_duration", "2");
	}});
	return subscription.get("id");

#### Subscription - cancel

	Subscription subscription = new Subscription("SUBSCRIPTION_ID");
		subscription = (Subscription)(subscription.cancel());
	return subscription.isActive();

### Signature calculation - Widget

	Signature.Widget widgetSignatureModel = new Signature.Widget();
	return widgetSignatureModel.calculate(
		new LinkedHashMap<String, ArrayList<String>>(), // widget params
		2 // signature version
	);

### Singature calculation - Pingback

    Signature.Pingback pingbackSignatureModel = new Signature.Pingback();
    return pingbackSignatureModel.calculate(
        new LinkedHashMap<String, ArrayList<String>>(), // widget params
        1 // signature version
    );
