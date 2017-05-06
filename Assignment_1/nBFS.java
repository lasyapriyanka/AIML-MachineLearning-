import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class EightPuzzle {

    Queue<String> LL = new LinkedList<String>();    
    Map<String,Integer> Depth = new HashMap<String, Integer>(); 
    Map<String,String> History = new HashMap<String,String>(); 

    public static void main(String args[]){

        String str="087465132";                                 

        EightPuzzle e = new EightPuzzle();              
        e.add(str, null);                                                   

        while(!e.LL.isEmpty()){
            String currentState = e.LL.remove();
            e.up(currentState);                                       
            e.down(currentState);                                     
            e.left(currentState);                                     
            e.right(currentState);                          
        }

        System.out.println("Solution doesn't exist");
    }

    
    void add(String newState, String oldState){
        if(!Depth.containsKey(newState)){
            int newValue = oldState == null ? 0 : Depth.get(oldState) + 1;
            Depth.put(newState, newValue);
            LL.add(newState);
            History.put(newState, oldState);
        }
    }



    void up(String currentState){
        int a = currentState.indexOf("0");
        if(a>2){
            String nextState = currentState.substring(0,a-3)+"0"+currentState.substring(a-2,a)+currentState.charAt(a-3)+currentState.substring(a+1);
            checkCompletion(currentState, nextState);
        }
    }

    void down(String currentState){
        int a = currentState.indexOf("0");
        if(a<6){
            String nextState = currentState.substring(0,a)+currentState.substring(a+3,a+4)+currentState.substring(a+1,a+3)+"0"+currentState.substring(a+4);
            checkCompletion(currentState, nextState);
        }
    }
    void left(String currentState){
        int a = currentState.indexOf("0");
        if(a!=0 && a!=3 && a!=6){
            String nextState = currentState.substring(0,a-1)+"0"+currentState.charAt(a-1)+currentState.substring(a+1);
            checkCompletion(currentState, nextState);
        }
    }
    void right(String currentState){
        int a = currentState.indexOf("0");
        if(a!=2 && a!=5 && a!=8){
            String nextState = currentState.substring(0,a)+currentState.charAt(a+1)+"0"+currentState.substring(a+2);
            checkCompletion(currentState, nextState);
        }
    }

    private void checkCompletion(String oldState, String newState) {
        add(newState, oldState);
        if(newState.equals("123456780")) {
            System.out.println("Solution Exists at Level "+Depth.get(newState)+" of the tree");
            String traceState = newState;
            while (traceState != null) {
                System.out.println(traceState + " at " + Depth.get(traceState));
                traceState = History.get(traceState);
            }
            System.exit(0);
        }
    }

}