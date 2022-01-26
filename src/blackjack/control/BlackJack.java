/*
Mi tarea es completar la función que reparte cartas para el crupier (última fase de la partida de
Black Jack) y devuelve los jugadores que han ganado.

Por tanto, resulta conveniente documentar las adaptaciones realizadas para
este código de 1 sola funcion.

Las reglas son las siguientes:
1.- Cada partida consta de 3 jugadores ("Player1", "Player2", "Player3") que juegan de forma
individual contra el crupier (los jugadores no juegan entre ellos).

2.- Cada carta tiene su valor: los números valen lo que indique su número; "J", "Q" y "K" valen
10; "A" puede valer 11 o 1.
-en esta implementación, las cartas poseen un método para obtener su valor. En
el caso de ser un as, se devuelve un 11. El cómputo de cartas se modifica en la función
getWinners.-

3.- Si un jugador excede de 21 puntos pierde

4.- El crupier debe sacar carta del montón hasta que su mano tenga 17 puntos o más.
-El crupier es el unico parámetro que puede seguir sacando cartas del mazo una vez
llamada esta función.-

5.- Un jugador tiene un Black Jack cuando tiene 2 cartas, una con valor 10 y la otra es una "A".
6.- Si el jugador tiene Black Jack, gana, a menos que el crupier también tenga Black Jack.

-estas reglas no afectan a mi código ya que ganadores>=0 y lo único que se tiene
en cuenta es la suma total del valor de las cartas.-

7.- Cuando el crupier saca una carta, el crupier siempre saca la primera carta del motón. 
 */
package blackjack.control;
import blackjack.model.Card;
import java.util.ArrayList;
/**
 *
 * @author edwin
 *
 */
public class BlackJack {
    
    public ArrayList<Object> getWinners(ArrayList<Card> p1, 
            ArrayList<Card> p2, ArrayList<Card> p3, ArrayList<Card> crupier,
            ArrayList<Card> deck){
        ArrayList<Object> result = new ArrayList<>();
        result.add(p1);
        result.add(p2);
        result.add(p3);
        
        int totalScore = 0;
        int aceCount = 0;
        int[] temp = sumCards(crupier);
        totalScore = temp[0];
        aceCount = temp[1];
        while(totalScore < 17){
            crupier.add(deck.get(0));
            totalScore += deck.get(0).getValue();
            deck.remove(0);
            if(totalScore > 21){
               temp = recomputeCards(totalScore,aceCount);
               totalScore = temp[0];
               aceCount = temp[1];
            }    
        }
        
        int scoreMinimum = totalScore;
        
        if(scoreMinimum > 21){
            for(Object player: result){
                temp = sumCards((ArrayList<Card>) player);
                totalScore = temp[0];
                aceCount = temp[1];
                recomputeCards(totalScore,aceCount);
                if(totalScore > 21){
                    result.remove(player);
                }
            }
        }else if(scoreMinimum == 21){
            return new ArrayList<>();
        }else{
            for(Object player: result){
                temp = sumCards((ArrayList<Card>) player);
                totalScore = temp[0];
                aceCount = temp[1];
                recomputeCards(totalScore,aceCount);
                if(totalScore > 21 || totalScore <= scoreMinimum){
                    result.remove(player);
                }
            }
        }         
        return result;
    }
    
    public int[] sumCards(ArrayList<Card> player){
        int totalScore = 0;
        int aceCount = 0;
        int[] result = new int[2];
        for(Card card:player){
            if(card.getSign().equals("ace")){
                totalScore += card.getValue();
                aceCount++;
            }else{
                totalScore += card.getValue();
            }
        }
        result[0] = totalScore;
        result[1] = aceCount;
        return result;
    }
    
    public int[] recomputeCards(int totalScore, int aceCount){
        int[] result = new int[2];
        while(totalScore > 21 && aceCount > 1){
            totalScore -= 10;
            aceCount--;
        }
        return result;
    }
}
