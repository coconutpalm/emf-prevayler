/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package transactionmodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Transaction Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see transactionmodel.TransactionmodelPackage#getTransactionType()
 * @model
 * @generated
 */
public enum TransactionType implements Enumerator {
	/**
	 * The '<em><b>Sure Transaction With Query</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SURE_TRANSACTION_WITH_QUERY_VALUE
	 * @generated
	 * @ordered
	 */
	SURE_TRANSACTION_WITH_QUERY(0, "SureTransactionWithQuery", "SureTransactionWithQuery"),

	/**
	 * The '<em><b>Transaction</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRANSACTION_VALUE
	 * @generated
	 * @ordered
	 */
	TRANSACTION(1, "Transaction", "Transaction"),

	/**
	 * The '<em><b>Transaction With Query</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRANSACTION_WITH_QUERY_VALUE
	 * @generated
	 * @ordered
	 */
	TRANSACTION_WITH_QUERY(2, "TransactionWithQuery", "TransactionWithQuery");

	/**
	 * The '<em><b>Sure Transaction With Query</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sure Transaction With Query</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SURE_TRANSACTION_WITH_QUERY
	 * @model name="SureTransactionWithQuery"
	 * @generated
	 * @ordered
	 */
	public static final int SURE_TRANSACTION_WITH_QUERY_VALUE = 0;

	/**
	 * The '<em><b>Transaction</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Transaction</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TRANSACTION
	 * @model name="Transaction"
	 * @generated
	 * @ordered
	 */
	public static final int TRANSACTION_VALUE = 1;

	/**
	 * The '<em><b>Transaction With Query</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Transaction With Query</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TRANSACTION_WITH_QUERY
	 * @model name="TransactionWithQuery"
	 * @generated
	 * @ordered
	 */
	public static final int TRANSACTION_WITH_QUERY_VALUE = 2;

	/**
	 * An array of all the '<em><b>Transaction Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TransactionType[] VALUES_ARRAY =
		new TransactionType[] {
			SURE_TRANSACTION_WITH_QUERY,
			TRANSACTION,
			TRANSACTION_WITH_QUERY,
		};

	/**
	 * A public read-only list of all the '<em><b>Transaction Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TransactionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Transaction Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransactionType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TransactionType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Transaction Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransactionType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TransactionType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Transaction Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransactionType get(int value) {
		switch (value) {
			case SURE_TRANSACTION_WITH_QUERY_VALUE: return SURE_TRANSACTION_WITH_QUERY;
			case TRANSACTION_VALUE: return TRANSACTION;
			case TRANSACTION_WITH_QUERY_VALUE: return TRANSACTION_WITH_QUERY;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TransactionType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //TransactionType
