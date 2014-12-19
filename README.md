#About Paymentwall
[Paymentwall](http://paymentwall.com/?source=gh) is the leading digital payments platform for globally monetizing digital goods and services. Paymentwall assists game publishers, dating sites, rewards sites, SaaS companies and many other verticals to monetize their digital content and services. 
Merchants can plugin Paymentwall's API to accept payments from over 100 different methods including credit cards, debit cards, bank transfers, SMS/Mobile payments, prepaid cards, eWallets, landline payments and others. 

To sign up for a Paymentwall Merchant Account, [click here](http://paymentwall.com/signup/merchant?source=gh).

#Paymentwall Java Library
This library allows developers to use [Paymentwall APIs](http://paymentwall.com/en/documentation/API-Documentation/722?source=gh) (Virtual Currency, Digital Goods featuring recurring billing, and Virtual Cart).

To use Paymentwall, all you need to do is to sign up for a Paymentwall Merchant Account so you can setup an Application designed for your site.
To open your merchant account and set up an application, you can [sign up here](http://paymentwall.com/signup/merchant?source=gh).

#Installation
To install the library in your environment, you can download the [ZIP archive](https://github.com/paymentwall/paymentwall-java/archive/master.zip), unzip it and place into your project.

Alternatively, you can run:

  <code> git clone git://github.com/paymentwall/paymentwall-java.git </code>

Then use a code sample below.

#Code Samples

##Adding Paymentwall to your project using Apache Maven

    <dependencies>
      <dependency>
        <groupId>com.paymentwall</groupId>
        <artifactId>java</artifactId>
        <version>1.0</version>
      </dependency>
    </dependencies>

##Digital Goods API

####Initializing Paymentwall

    @page import com.paymentwall.java.*;
    PaymentwallBase.setAppKey("YOUR_APPLICATION_KEY");
    PaymentwallBase.setSecretKey("YOUR_SECRET_KEY");
    PaymentwallBase.setApiType(PaymentwallBase.API_GOODS);

####Widget Call

[Web API details](http://www.paymentwall.com/en/documentation/Digital-Goods-API/710#paymentwall_widget_call_flexible_widget_call)

The widget is a payment page hosted by Paymentwall that embeds the entire payment flow: selecting the payment method, completing the billing details, and providing customer support via the Help section. You can redirect the users to this page or embed it via iframe. Below is an example that renders an iframe with Paymentwall Widget.

    final PaymentwallProduct product = new PaymentwallProductBuilder("product301") {{
      setAmount(0.99);
      setCurrencyCode("USD");
      setName("100 coins");
      setProductType(PaymentwallProduct.TYPE_FIXED);
    }}.buildPaymentwallProduct();
    PaymentwallWidgetBuilder widgetBuilder = new PaymentwallWidgetBuilder("user12345","p1_1");
    widgetBuilder.setProduct(product);
    PaymentwallWidget widget = widgetBuilder.build();
    out.println(widget.getHtmlCode());

####Pingback Processing

The Pingback is a webhook notifying about a payment being made. Pingbacks are sent via HTTP/HTTPS to your servers. To process pingbacks use the following code:

    PaymentwallPingback pingback = new PaymentwallPingback(PaymentwallBase.parseQuery(request.getParameterMap()), request.getRemoteAddr());
    if (pingback.validate(true)) {
      String goods = pingback.getProductId();
      String userId = pingback.getUserId();
      if (pingback.isDeliverable()) {
        // deliver Product to user with userId
      } else if (pingback.isCancelable()) {
        // withdraw Product from user with userId
      }
      out.println("OK");
    } else
      out.println(pingback.getErrorSummary());

##Virtual Currency API

####Initializing Paymentwall

    @page import com.paymentwall.java.*;
    PaymentwallBase.setAppKey("YOUR_APPLICATION_KEY");
    PaymentwallBase.setSecretKey("YOUR_SECRET_KEY");
    PaymentwallBase.setApiType(PaymentwallBase.API_VC);

####Widget Call

    @page import java.util.ArrayList;
    @page import java.util.LinkedHashMap;
    PaymentwallWidgetBuilder widgetBuilder = new PaymentwallWidgetBuilder("user12345","p1_1");
    LinkedHashMap<String,ArrayList<String>> additionalparameters = new LinkedHashMap<String, ArrayList<String>>();
    additionalparameters.put("email",new ArrayList<String>(){{add("user@hostname.com");}});
    widgetBuilder.setExtraParams(additionalparameters);
    PaymentwallWidget widget = widgetBuilder.build();
    out.println(widget.getHtmlCode());

####Pingback Processing

    PaymentwallPingback pingback = new PaymentwallPingback(PaymentwallBase.parseQuery(request.getParameterMap()), request.getRemoteAddr());
    if (pingback.validate(true)) {
      Double currency = pingback.getVirtualCurrencyAmount();
      String userId = pingback.getUserId();
      if (pingback.isDeliverable()) {
        // deliver Product to user with userId
      } else if (pingback.isCancelable()) {
        // withdraw Product from user with userId
      }
      out.println("OK");
    } else
      out.println(pingback.getErrorSummary());

##Cart API

####Initializing Paymentwall

    @page import com.paymentwall.java.*;
    PaymentwallBase.setAppKey("YOUR_APPLICATION_KEY");
    PaymentwallBase.setSecretKey("YOUR_SECRET_KEY");
    PaymentwallBase.setApiType(PaymentwallBase.API_CART);


You have to set up your products in merchant area for exact regions first in order to use widget call example code below.

    PaymentwallWidgetBuilder widgetBuilder = new PaymentwallWidgetBuilder("user12345","p1_1");
    LinkedHashMap<String,ArrayList<String>> additionalparameters = new LinkedHashMap<String, ArrayList<String>>();
    additionalparameters.put("email",new ArrayList<String>(){{add("user@hostname.com");}});
    final PaymentwallProduct product1 = new PaymentwallProductBuilder("product1"){{
      setAmount(9.99);
      setCurrencyCode("USD");
    }}.buildPaymentwallProduct();
    final PaymentwallProduct product2 = new PaymentwallProductBuilder("product2"){{
      setAmount(1);
      setCurrencyCode("USD");
    }}.buildPaymentwallProduct();
    widgetBuilder.setExtraParams(additionalparameters);
    widgetBuilder.setProducts(new ArrayList<PaymentwallProduct>(){{
      add(product1);
      add(product2);
    }});
    PaymentwallWidget widget = widgetBuilder.build();
    out.println(widget.getHtmlCode());


####Pingback Processing

    PaymentwallPingback pingback = new PaymentwallPingback(PaymentwallBase.parseQuery(request.getParameterMap()), request.getRemoteAddr());
    if (pingback.validate(true)) {
      ArrayList<PaymentwallProduct> goods = pingback.getProducts();
      String userId = pingback.getUserId();
      if (pingback.isDeliverable()) {
        // deliver Product to user with userId
      } else if (pingback.isCancelable()) {
        // withdraw Product from user with userId
      }
      out.println("OK");
    } else
      out.println(pingback.getErrorSummary());

