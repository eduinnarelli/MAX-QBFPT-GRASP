/**
 * GRASP metaheuristic abstract class.
 */
package metaheuristics.grasp;

import java.util.ArrayList;
import java.util.Random;
import problems.Evaluator;
import solutions.Solution;

import metaheuristics.grasp.reactive.Alpha;
import metaheuristics.grasp.reactive.AlphaMap;

/**
 * Abstract class for metaheuristic GRASP (Greedy Randomized Adaptive Search
 * Procedure). It consider a minimization problem.
 * 
 * @author ccavellucci, fusberti, aserpa, einnarelli
 * @param <E>
 *            Generic type of the element which composes the solution.
 */
public abstract class AbstractGRASP<E> {

	/**
	 * Flag that indicates whether the code should print more information on
	 * screen.
	 */
	public static boolean verbose = true;

	/**
	 * A random number generator.
	 */
	static Random rng = new Random(0);

	/**
	 * The objective function being optimized.
	 */
	protected Evaluator<E> ObjFunction;

	/**
	 * The non-reactive GRASP greediness-randomness parameter.
	 */
	protected Double alpha;
	
	/**
	 * The reactive GRASP number of possible alphas ({@link #alpha}) to be
	 * selected.
	 */
	protected Integer numAlphas;

	/**
	 * The best solution cost.
	 */
	protected Double incumbentCost;

	/**
	 * The current solution cost.
	 */
	protected Double currentCost;

	/**
	 * The best solution.
	 */
	protected Solution<E> incumbentSol;

	/**
	 * The incumbent solution.
	 */
	protected Solution<E> currentSol;

	/**
	 * The number of iterations the GRASP main loop executes.
	 */
	protected Integer iterations;

	/**
	 * The Candidate List of elements to enter the solution.
	 */
	protected ArrayList<E> CL;

	/**
	 * The Restricted Candidate List of elements to enter the solution.
	 */
	protected ArrayList<E> RCL;

	/**
	 * Creates the Candidate List, which is an ArrayList of candidate elements
	 * that can enter a solution.
	 * 
	 * @return The Candidate List.
	 */
	public abstract ArrayList<E> makeCL();

	/**
	 * Creates the Restricted Candidate List, which is an ArrayList of the best
	 * candidate elements that can enter a solution. The best candidates are
	 * defined through a quality threshold, delimited by the GRASP
	 * {@link #alpha} greedyness-randomness parameter.
	 * 
	 * @return The Restricted Candidate List.
	 */
	public abstract ArrayList<E> makeRCL();

	/**
	 * Updates the Candidate List according to the incumbent solution
	 * {@link #currentSol}. In other words, this method is responsible for
	 * updating which elements are still viable to take part into the solution.
	 */
	public abstract void updateCL();

	/**
	 * Creates a new solution which is empty, i.e., does not contain any
	 * element.
	 * 
	 * @return An empty solution.
	 */
	public abstract Solution<E> createEmptySol();

	/**
	 * The GRASP local search phase is responsible for repeatedly applying a
	 * neighborhood operation while the solution is getting improved, i.e.,
	 * until a local optimum is attained.
	 * 
	 * @return An local optimum solution.
	 */
	public abstract Solution<E> localSearch();


	/**
	 * Base constructor for the AbstractGRASP class, only called inside the 
	 * class.
	 * 
	 * @param objFunction
	 * 		The objective function being minimized.
	 * @param iterations
	 * 		The number of iterations which the GRASP will be executed.
	 */
	private AbstractGRASP(Evaluator<E> objFunction, Integer iterations) {
		this.ObjFunction = objFunction;
		this.iterations = iterations;
	}

	/**
	 * Constructor for the non-reactive AbstractGRASP class, which calls the
	 * base constructor ({@link #AbstractGRASP(Evaluator, Integer)}).
	 * 
	 * @param alpha
	 *		The GRASP greediness-randomness parameter (within the range [0,1]).
	 */
	public AbstractGRASP(Evaluator<E> objFunction, Integer iterations, Double alpha) {
		this(objFunction, iterations);
		assert alpha >= 0.0 && alpha <= 1.0 : "alpha should be a double in [0, 1].";
		this.alpha = alpha;
	}

	/**
	 * Constructor for the reactive AbstractGRASP class, which calls the base
	 * constructor ({@link #AbstractGRASP(Evaluator, Integer)}).
	 * 
	 * @param numAlphas
	 *		Number of possible alphas to be selected.
	 */
	public AbstractGRASP(
		Evaluator<E> objFunction, 
		Integer iterations, 
		Integer numAlphas
	) {
		this(objFunction, iterations);
		assert numAlphas >= 1 : "numAlphas should be a positive integer.";
		this.numAlphas = numAlphas;
	}

	/**
	 * The GRASP constructive heuristic, which is responsible for building a
	 * feasible solution by selecting in a greedy-random fashion, candidate
	 * elements to enter the solution.
	 * 
	 * @return A feasible solution to the problem being minimized.
	 */
	public Solution<E> constructiveHeuristic() {

		CL = makeCL();
		RCL = makeRCL();
		currentSol = createEmptySol();
		currentCost = Double.POSITIVE_INFINITY;

		/* Main loop, which repeats until the stopping criteria is reached. */
		while (!constructiveStopCriteria()) {

			double maxCost = Double.NEGATIVE_INFINITY, minCost = Double.POSITIVE_INFINITY;
			currentCost = ObjFunction.evaluate(currentSol);
			updateCL();

			/*
			 * Explore all candidate elements to enter the solution, saving the
			 * highest and lowest cost variation achieved by the candidates.
			 */
			for (E c : CL) {
				Double deltaCost = ObjFunction.evaluateInsertionCost(c, currentSol);
				if (deltaCost < minCost)
					minCost = deltaCost;
				if (deltaCost > maxCost)
					maxCost = deltaCost;
			}

			/*
			 * Among all candidates, insert into the RCL those with the highest
			 * performance using parameter alpha as threshold.
			 */
			for (E c : CL) {
				Double deltaCost = ObjFunction.evaluateInsertionCost(c, currentSol);
				if (deltaCost <= minCost + alpha * (maxCost - minCost)) {
					RCL.add(c);
				}
			}

			/* Choose a candidate randomly from the RCL */
			int rndIndex = rng.nextInt(RCL.size());
			E inCand = RCL.get(rndIndex);
			CL.remove(inCand);
			currentSol.add(inCand);
			ObjFunction.evaluate(currentSol);
			RCL.clear();

		}

		return currentSol;
	}

	/**
	 * The GRASP mainframe. It consists of a loop, in which each iteration goes
	 * through the constructive heuristic and local search. The best solution is
	 * returned as result.
	 * 
	 * @return The best feasible solution obtained throughout all iterations.
	 */
	public Solution<E> solve() {

		incumbentSol = createEmptySol();

		/* Reactive GRASP alpha list, not instantiated in the non-reactive 
		 * GRASP. */
		AlphaMap alphaSet = null;

		/* Reactive GRASP only happens if numAlphas was received in the
		 * constructor, instead of alpha. */
		boolean isReactive = numAlphas != null;

		// Reactive GRASP:
		if (isReactive) {
			// Initialize alpha list.
			alphaSet = new AlphaMap(numAlphas);
		}

		for (int i = 0; i < iterations; i++) {

			// Reactive GRASP:
			if (isReactive) {
				// Select a random alpha from the weighted list.
				alpha = alphaSet.selectItem();
			}

			// Greedy-random construction.
			constructiveHeuristic();

			// Try to improve the solution.
			localSearch();

			// Update incumbent solution, if necessary.
			if (incumbentSol.cost > currentSol.cost) {
				incumbentSol = new Solution<E>(currentSol);
				if (verbose)
					System.out.println("(Iter. " + i + ") BestSol = " + incumbentSol + ", alpha=" + alpha);
			}

			// Reactive GRASP:
			if (isReactive && i < iterations - 1) {

				// Update average cost of solutions that used alpha.
				((Alpha) alphaSet.get(alpha)).updateA(currentSol.cost);

				// Update the alpha probabilities at each 10 iterations.
				if ((i + 1) % 10 == 0) {
					alphaSet.updateProbabilities(incumbentSol.cost);
				}

			}

		}

		return incumbentSol;
	}

	/**
	 * A standard stopping criteria for the constructive heuristic is to repeat
	 * until the incumbent solution improves by inserting a new candidate
	 * element.
	 * 
	 * @return true if the criteria is met.
	 */
	public Boolean constructiveStopCriteria() {
		return (currentCost > currentSol.cost) ? false : true;
	}

}
