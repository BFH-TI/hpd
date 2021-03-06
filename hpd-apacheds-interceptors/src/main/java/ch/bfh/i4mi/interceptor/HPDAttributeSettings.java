package ch.bfh.i4mi.interceptor;

/**
 * The Class HPDAttributeSettings represents the special HPD settings for an attribute.
 * 
 * @author Kevin Tippenhauer, Berner Fachhochschule
 */
public class HPDAttributeSettings {
	
	/** The attribute name. */
	private String attributeName;
	
	/** The is single value. */
	private Boolean isSingleValue;
	
	/** The is optional. */
	private Boolean isOptional;
	
	/** The number of min occurence. */
	private int numberOfMinOccurence;
	
	/** The number of max occurence. */
	private int numberOfMaxOccurence;
	
	
	/**
	 * Sets the attribute name.
	 *
	 * @param anAttributeName the new attribute name
	 * @throws IllegalArgumentException the exception thrown if the arguments are illegal
	 */
	public final void setAttributeName(final String anAttributeName) throws IllegalArgumentException {
		if (anAttributeName == null || anAttributeName.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.attributeName = anAttributeName;
	}

	/**
	 * Sets the single value option.
	 *
	 * @param anIsSingleValue the new checks if is single value
	 * @throws IllegalArgumentException the exception thrown if the arguments are illegal
	 */
	public final void setIsSingleValue(final Boolean anIsSingleValue) throws IllegalArgumentException {
		if (anIsSingleValue == null) {
			throw new IllegalArgumentException();
		}
		this.isSingleValue = anIsSingleValue;
	}

	/**
	 * Sets the checks if is optional.
	 *
	 * @param anIsOptional the new checks if is optional
	 * @throws IllegalArgumentException the exception thrown if the arguments are illegal
	 */
	public final void setIsOptional(final Boolean anIsOptional) throws IllegalArgumentException {
		if (anIsOptional == null) {
			throw new IllegalArgumentException();
		}
		this.isOptional = anIsOptional;
	}

	/**
	 * Sets the minimal and maximal occurrence of the attribute.
	 *
	 * @param min the minimal occurrence
	 * @param max the maximal occurrence
	 * @throws IllegalArgumentException the exception thrown if the arguments are illegal
	 */
	public final void setMinMaxOccurence(final int min, final int max) throws IllegalArgumentException {
		
		if (min < 0) {
			throw new IllegalArgumentException("'min' is lesser than 0!");
		} else if (max < 1) {
			throw new IllegalArgumentException("'max' is lesser than 1!");
		} else if (min > max) {
			throw new IllegalArgumentException("'max' is lesser than 'min'!");
		}
		this.numberOfMinOccurence = min;
		this.numberOfMaxOccurence = max;
	}
	
	/**
	 * Gets the attribute name.
	 *
	 * @return the attribute name
	 */
	public final String getAttributeName() {
		return attributeName;
	}
	
	/**
	 * Checks if the attribute is single value.
	 *
	 * @return True, if the attribute is single
	 */
	public final Boolean isSingleValue() {
		return isSingleValue;
	}
	
	/**
	 * Checks if the attribute is optional.
	 *
	 * @return True, if the attribute is optional
	 */
	public final Boolean isOptional() {
		return isOptional;
	}
	
	/**
	 * Gets the number of min occurence for the attribute.
	 *
	 * @return the number of min occurence
	 */
	public final int getNumberOfMinOccurence() {
		return numberOfMinOccurence;
	}
	
	/**
	 * Gets the number of max occurence for the attribute.
	 *
	 * @return the number of max occurence
	 */
	public final int getNumberOfMaxOccurence() {
		return numberOfMaxOccurence;
	}
	
	/**
	 * Instantiates a new HPD attribute settings object.
	 *
	 * @param anAttributeName the an attribute name
	 * @param anIsSingleValue the an is single value
	 * @param anIsOptional the an is optional
	 * @param aNumberOfMinOccurence the a number of min occurence
	 * @param aNumberOfMaxOccurence the a number of max occurence
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public HPDAttributeSettings(final String anAttributeName,
						final Boolean anIsSingleValue,
						final Boolean anIsOptional,
						final int aNumberOfMinOccurence,
						final int aNumberOfMaxOccurence) throws IllegalArgumentException {
		
		this.setAttributeName(anAttributeName);
		this.setIsSingleValue(anIsSingleValue);
		this.setIsOptional(anIsOptional);
		this.setMinMaxOccurence(aNumberOfMinOccurence, aNumberOfMaxOccurence);
	}
}
