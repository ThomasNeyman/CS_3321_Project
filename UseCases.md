## User rolls dice  
1. User clicks on dice icon  
2. System randomly selects value, and sends value to the server  
3. cs3321.MoServer computes players need position and returns value to user  
4. System moves players piece  
### Alternate flows  
A. Player can buy a property   
B. Player lands on a chance card   
C. Player starts in jail  
E. It’s not that players turn  
F. Player exits the game.  

## User buys a property   
1. User is already on a property that is unowned  
2. User clicked the yes button and buys the property  
3. System sends information to server  
4. cs3321.MoServer removes funds from users bank and add property to users property list  
### Alternate flows  
A. User doesn’t buy property  
B. User doesn’t buy property and opponent does  

## User connects  
1. User enters port #  
2. System connect to server  
3. System moves to play screen  
### Alternate flows   
A. User enters wrong port #  
B. cs3321.MoServer already has two players  
