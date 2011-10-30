/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package transactionmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see transactionmodel.TransactionmodelFactory
 * @model kind="package"
 * @generated
 */
public interface TransactionmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "transactionmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://es.makestrid.premf.transactionmodel/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "es.makestrid.premf.transactionmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransactionmodelPackage eINSTANCE = transactionmodel.impl.TransactionmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link transactionmodel.impl.ChangeTransactionImpl <em>Change Transaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see transactionmodel.impl.ChangeTransactionImpl
	 * @see transactionmodel.impl.TransactionmodelPackageImpl#getChangeTransaction()
	 * @generated
	 */
	int CHANGE_TRANSACTION = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_TRANSACTION__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_TRANSACTION__CHANGE = 1;

	/**
	 * The number of structural features of the '<em>Change Transaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_TRANSACTION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link transactionmodel.TransactionType <em>Transaction Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see transactionmodel.TransactionType
	 * @see transactionmodel.impl.TransactionmodelPackageImpl#getTransactionType()
	 * @generated
	 */
	int TRANSACTION_TYPE = 1;

	/**
	 * The meta object id for the '<em>Change Description</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.change.ChangeDescription
	 * @see transactionmodel.impl.TransactionmodelPackageImpl#getChangeDescription()
	 * @generated
	 */
	int CHANGE_DESCRIPTION = 2;


	/**
	 * Returns the meta object for class '{@link transactionmodel.ChangeTransaction <em>Change Transaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Transaction</em>'.
	 * @see transactionmodel.ChangeTransaction
	 * @generated
	 */
	EClass getChangeTransaction();

	/**
	 * Returns the meta object for the attribute '{@link transactionmodel.ChangeTransaction#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see transactionmodel.ChangeTransaction#getType()
	 * @see #getChangeTransaction()
	 * @generated
	 */
	EAttribute getChangeTransaction_Type();

	/**
	 * Returns the meta object for the attribute '{@link transactionmodel.ChangeTransaction#getChange <em>Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Change</em>'.
	 * @see transactionmodel.ChangeTransaction#getChange()
	 * @see #getChangeTransaction()
	 * @generated
	 */
	EAttribute getChangeTransaction_Change();

	/**
	 * Returns the meta object for enum '{@link transactionmodel.TransactionType <em>Transaction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Transaction Type</em>'.
	 * @see transactionmodel.TransactionType
	 * @generated
	 */
	EEnum getTransactionType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.change.ChangeDescription <em>Change Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Change Description</em>'.
	 * @see org.eclipse.emf.ecore.change.ChangeDescription
	 * @model instanceClass="org.eclipse.emf.ecore.change.ChangeDescription"
	 * @generated
	 */
	EDataType getChangeDescription();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TransactionmodelFactory getTransactionmodelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link transactionmodel.impl.ChangeTransactionImpl <em>Change Transaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see transactionmodel.impl.ChangeTransactionImpl
		 * @see transactionmodel.impl.TransactionmodelPackageImpl#getChangeTransaction()
		 * @generated
		 */
		EClass CHANGE_TRANSACTION = eINSTANCE.getChangeTransaction();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_TRANSACTION__TYPE = eINSTANCE.getChangeTransaction_Type();

		/**
		 * The meta object literal for the '<em><b>Change</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_TRANSACTION__CHANGE = eINSTANCE.getChangeTransaction_Change();

		/**
		 * The meta object literal for the '{@link transactionmodel.TransactionType <em>Transaction Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see transactionmodel.TransactionType
		 * @see transactionmodel.impl.TransactionmodelPackageImpl#getTransactionType()
		 * @generated
		 */
		EEnum TRANSACTION_TYPE = eINSTANCE.getTransactionType();

		/**
		 * The meta object literal for the '<em>Change Description</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.change.ChangeDescription
		 * @see transactionmodel.impl.TransactionmodelPackageImpl#getChangeDescription()
		 * @generated
		 */
		EDataType CHANGE_DESCRIPTION = eINSTANCE.getChangeDescription();

	}

} //TransactionmodelPackage
