@automated
Feature: Place a specific order for moisturizers or sunscreens based on current temperature.

   Shop for moisturizers if current temperature is lower than 19 degrees.
   Shop for sunscreens if current temperature is higher than 34 degrees.
   When shopping for moisturizers, add two items into the cart:
   1. least expensive mositurizer that contains Aloe
   2. least expensive moisturizer that contains almond
   When shopping for sunscreens, add two items into the cart:
   1. least expensive sunscreen that is SPF-50
   2. least expensive sunscreen that is SPF-30
   Fill out payment details and verify that payment was successful.

      Scenario: Place new order
         Given Customer navigates to home page
         When Customer clicks correct button based on temperature
         Then Correct category page is displayed
         When Customer adds first item into cart
         And Customer adds second item into cart
         And Customer clicks cart button
         Then Items are displayed in cart
         When Customer enters payment details
         And Customer submits payment form
         Then Payment was successful