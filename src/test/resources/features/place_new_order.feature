@automated

Feature: Place new order  
   Customer wants to place a specific order for moisturizers or sunscreens based on current temperature

      Background:
         Given Customer navigates to home page

      Scenario: Get to 'Moisturizers' product category
         Given Current temperature on home page is <19 degrees
         When Customer clicks on 'Buy moisturizers' button
         Then Moisturizers category page is displayed

      Scenario: Get to 'Sunscreens' product category
         Given Current temperature on home page is >34 degrees
         When Customer clicks on 'Buy sunscreens' button
         Then Sunscreens category page is displayed