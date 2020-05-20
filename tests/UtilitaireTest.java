import org.junit.Assert;
import org.junit.Test;

public class UtilitaireTest {

    @Test
    public void testDistanceEuclidienne() {

        //Premier test de distance euclidienne
        double distanceEuclidienne1 = utilitaires.Utilitaire.distanceEuclidienne(2, 6, 7, 4);
        double distanceEuclidienneExpected1 = Math.sqrt(Math.pow(7 - 2, 2) + Math.pow(4 - 6, 2));
        Assert.assertEquals(distanceEuclidienneExpected1, distanceEuclidienne1,0.001);
        System.out.println("= Test 1 distance euclidienne passed ✅ =");

        //Deuxième test de distance euclidienne
        double distanceEuclidienne2 = utilitaires.Utilitaire.distanceEuclidienne(1423, 0, 80, 3);
        double distanceEuclidienneExpected2 = Math.sqrt(Math.pow(80 - 1423, 2) + Math.pow(3 - 0, 2));
        Assert.assertEquals(distanceEuclidienneExpected2, distanceEuclidienne2,0.001);
        System.out.println("= Test 2 distance euclidienne passed ✅ =");

        //Troisième test de distance euclidienne
        double distanceEuclidienne3 = utilitaires.Utilitaire.distanceEuclidienne(1, 4, 3, 2);
        double distanceEuclidienneExpected3 = Math.sqrt(Math.pow(3 - 1, 2) + Math.pow(2 - 4, 2));
        Assert.assertEquals(distanceEuclidienneExpected3, distanceEuclidienne3,0.001);
        System.out.println("= Test 3 distance euclidienne passed ✅ =");

    }

}
