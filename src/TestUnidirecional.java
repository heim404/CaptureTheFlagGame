import Game.Game;
import Maps.CustomNetworkADT;
import Maps.CustomNetworkUniDiretional;
import Game.GameImpl;
import java.io.IOException;

public class TestUnidirecional {
    public static void main(String[] args) throws IOException {
        //Game game = new GameImpl();

        //CRIACAO DE MAPA BIDIRECIONAL MapaBi
        CustomNetworkADT<Integer> customNetworkADT = new CustomNetworkUniDiretional<>();
        customNetworkADT.addVertex(0);
        customNetworkADT.addVertex(1);
        customNetworkADT.addVertex(2);
        customNetworkADT.addVertex(3);
        customNetworkADT.addVertex(4);
        customNetworkADT.addVertex(5);
        customNetworkADT.addVertex(6);
        customNetworkADT.addVertex(7);
        customNetworkADT.addVertex(8);
        customNetworkADT.addEdge(0, 2, 5);
        customNetworkADT.addEdge(2, 0, 5);
        customNetworkADT.addEdge(2, 4, 5);
        customNetworkADT.addEdge(4, 2, 5);
        customNetworkADT.addEdge(4, 6, 5);
        customNetworkADT.addEdge(6, 5, 5);
        customNetworkADT.addEdge(6, 8, 5);
        customNetworkADT.addEdge(8, 6, 5);

        customNetworkADT.addEdge(0, 1, 4);
        customNetworkADT.addEdge(1, 3, 4);
        customNetworkADT.addEdge(3, 6, 4);
        customNetworkADT.addEdge(4, 1, 4);
        customNetworkADT.addEdge(7, 4, 4);
        customNetworkADT.addEdge(7, 5, 4);
        customNetworkADT.addEdge(5, 2, 4);
        customNetworkADT.addEdge(8, 7, 4);
        customNetworkADT.exportToJson("MapaUni");
    }
}
