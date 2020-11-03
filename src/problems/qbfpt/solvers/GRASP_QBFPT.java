package problems.qbfpt.solvers;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import problems.qbf.solvers.GRASP_QBF;
import problems.qbfpt.QBFPT;
import solutions.Solution;

/**
 * Metaheuristic GRASP (Greedy Randomized Adaptive Search Procedure) for
 * obtaining an optimal solution to a {@link QBFPT} (Quadractive Binary 
 * Function with Prohibited Triples). 
 * 
 * @author aserpa, einnarelli
 */
public class GRASP_QBFPT extends GRASP_QBF {

    /**
     * The set T of prohibited triples.
     */
    private final Set<List<Integer>> T;

    /**
     * Constructor for the GRASP_QBFPT class.
     * 
     * @param alpha
     *            The GRASP greediness-randomness parameter (within the range
     *            [0,1])
     * @param iterations
     *            The number of iterations which the GRASP will be executed.
     * @param filename
     *            Name of the file for which the objective function parameters
     *            should be read.
     * @throws IOException
     *            Necessary for I/O operations.
     */
    public GRASP_QBFPT(Double alpha, Integer iterations, String filename) throws IOException {

        super(alpha, iterations, filename);

        // Instantiate QBFPT problem, store T and update objective reference.
        QBFPT qbfpt = new QBFPT(filename);
        T = qbfpt.getT();
        ObjFunction = qbfpt;

    }

    /*
    * (non-Javadoc)
    * 
    * @see grasp.abstracts.AbstractGRASP#updateCL()
    */
    @Override
    public void updateCL() {

        // Store numbers in solution and _CL as hash sets.
        Set<Integer> sol = new HashSet<Integer>(currentSol);
        Set<Integer> _CL = new HashSet<Integer>(CL);

        // Initialize _CL with all elements not in solution.
        for (Integer e = 0; e < ObjFunction.getDomainSize(); e++) {
            if (!sol.contains(e)) {
                _CL.add(e);
            }
        }

        for (List<Integer> t : T) {

            /**
             * Detach elements from (e1, e2, e3). They are stored as numbers 
             * from [0, n-1] in sol. and CL, different than in T ([1, n]).
             */
            Integer e1, e2, e3;
            e1 = t.get(0) - 1;
            e2 = t.get(1) - 1;
            e3 = t.get(2) - 1;

            // e1 and e2 in solution -> remove e3 from CL.
            if (sol.contains(e1) && sol.contains(e2)) {
                _CL.remove(e3); // if not in CL this has no effect
            }

            // e1 and e3 in solution -> remove e2 from CL.
            else if (sol.contains(e1) && sol.contains(e3)) {
                _CL.remove(e2); // if not in CL this has no effect
            }

            // e2 and e3 in solution -> remove e1 from CL.
            else if (sol.contains(e2) && sol.contains(e3)) {
                _CL.remove(e1); // if not in CL this has no effect
            }

        }

        CL = new ArrayList<Integer>(_CL);

    }

    /**
     * A main method used for testing the GRASP metaheuristic.
     */
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        GRASP_QBF grasp = new GRASP_QBFPT(0.05, 1000, "instances/qbf020");
        Solution<Integer> bestSol = grasp.solve();
        System.out.println("maxVal = " + bestSol);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time = "+(double)totalTime/(double)1000+" seg");

    }

}
