# 1 stage:
##  Create index.jsp page with buttons of methods of identification
![alt text](https://www.bankid.com/assets/bankid/rp/best-practise_images/BP_Desktop-Other_Example1.png)
- when client click button "BankID" our service create order to auth and send to bankID API<br />
**need to use:**
  > - Http client with parameters:
  >> POST /rp/v5.1/auth HTTP/1.1<br />
  Content-Type: application/json<br />
  Host: appapi2.bankid.com<br />
  {<br />
  "endUserIp": "194.168.2.25"<br />
  }<br />
  >
  > - Valid TLS certificate
  >>need add certificate from bankid.com to keystore of java
- if request to bankid API is success - we get
  parameters from response: 
  >orderRef<br />autoStartToken<br />qrStartToken<br />qrStartSecret
# 2 stage:
##  Generate QR-code
![alt text](https://www.bankid.com/assets/bankid/rp/best-practise_images/Desktop_QR.png)
**need to use:**
> - parameters from auth response
> - java code from example on bankid.com
> - keep on sending request every two seconds while status of order is pending,
example of POST request:
> >POST /rp/v5.1/collect HTTP/1.1<br />
    Content-Type: application/json<br />
    Host: appapi2.bankid.com<br />
    {<br />
    "orderRef":"131daac9-16c6-4618-beb0-365768f37288"<br />
    }<br />
>
> example of POST response:
> >HTTP/1.1 200 OK<br />
Content-Type: application/json<br />
{<br />
"orderRef":"131daac9-16c6-4618-beb0-365768f37288",<br />
"status":"pending",<br />
"hintCode":"userSign"<br />
}<br />
> - user must scan our QR-code or click "Use bakID on this deice"
>> if user click "Use bakID on this deice" we need to use URL from documentation
> to call window with the ability to open application
> - while status is pending or failed service returning
to client user massage from documentation
depending on the hintCode value
> - if status is complete we get response with user information, example:
>>HTTP/1.1 200 OK<br />
Content-Type: application/json<br />
{<br />
"orderRef":"131daac9-16c6-4618-beb0-365768f37288",<br />
"status":"complete",<br />
"completionData":{<br />
"user":{<br />
"personalNumber":"190000000000",<br />
"name":"Karl Karlsson",<br />
"givenName":"Karl",<br />
"surname":"Karlsson"<br />
},<br />
"device":{<br />
"ipAddress":"192.168.0.1"<br />
},<br />
"cert":{<br />
"notBefore":"1502983274000",<br />
"notAfter":"1563549674000"<br />
},<br />
"signature":"",<br />
"ocspResponse":""<br />
}<br />
}<br />
> - if client canceled authentication we get empty response
> - redirect client to new page with user information or
information of canceling or error message
