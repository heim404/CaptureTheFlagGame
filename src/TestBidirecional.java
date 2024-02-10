import Game.GameImpl;
import Maps.CustomNetworkADT;
import Maps.CustomNetworkBidirecional;

import java.io.IOException;

public class TestBidirecional {
    public static void main(String[] args) throws IOException {


        //GameImpl game = new GameImpl();


//CRIACAO DE MAPA BIDIRECIONAL MapaBi
      CustomNetworkADT<Integer> customNetworkADT = new CustomNetworkBidirecional<>();
        customNetworkADT.addVertex(0);
        customNetworkADT.addVertex(1);
        customNetworkADT.addVertex(2);
        customNetworkADT.addVertex(3);
        customNetworkADT.addVertex(4);
        customNetworkADT.addVertex(5);
        customNetworkADT.addVertex(6);
        customNetworkADT.addVertex(7);
        customNetworkADT.addEdge(0, 1, 5);
        customNetworkADT.addEdge(0, 2, 4);
        customNetworkADT.addEdge(1, 4, 7);
        customNetworkADT.addEdge(1, 5, 5);
        customNetworkADT.addEdge(2, 3, 2);
        customNetworkADT.addEdge(2, 5, 5);
        customNetworkADT.addEdge(3, 5, 4);
        customNetworkADT.addEdge(3, 6, 3);
        customNetworkADT.addEdge(4, 5, 2);
        customNetworkADT.addEdge(5, 6, 2);
        customNetworkADT.addEdge(5, 7, 5);
        customNetworkADT.addEdge(6, 7, 5);
        customNetworkADT.exportToJson("MapaBi");
    }
}
