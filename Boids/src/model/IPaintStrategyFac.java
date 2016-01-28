package model;

/**
 * An interface that defines a factory that instantiates 
 * a specific IPaintStrategy
 */
public interface IPaintStrategyFac {

	/**
	 * Instantiate the specific IPaintStrategyFac for which this factory is defined.
	 * @return An IPaintStrategy instance.
	 */
	public IPaintStrategy make();

}
