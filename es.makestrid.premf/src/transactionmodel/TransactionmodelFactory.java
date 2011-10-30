/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package transactionmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see transactionmodel.TransactionmodelPackage
 * @generated
 */
public interface TransactionmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransactionmodelFactory eINSTANCE = transactionmodel.impl.TransactionmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Change Transaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Transaction</em>'.
	 * @generated
	 */
	ChangeTransaction createChangeTransaction();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TransactionmodelPackage getTransactionmodelPackage();

} //TransactionmodelFactory
