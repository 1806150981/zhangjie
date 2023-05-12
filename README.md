//读文件
BillLearinigDiscountCsv billLearinigDiscountCsv = billLearinigDiscountCsvDao.readLine();

readline里的代码
package jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.biladpt2501002;

import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvFileAccess;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvFileAccessException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvFileNotFoundException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvIOException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvSecurityException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvUnsupportedEncodingException;

/**
 * <pre>
 * CSVファイルアクセス BillLearinigDiscountCsv のDAO実装クラス。
 * BillLearinigDiscountCsvDao。
 * </pre>
 * @author DHC
 * @version 2023/05/10 自動生成
 */
public class BillLearinigDiscountCsvDao extends CsvFileAccess {

	/**
	 * <pre>
	 * データアクセスオブジェクト名。
	 * </pre>
	 */
	public static final String DAO_NAME = BillLearinigDiscountCsvDao.class.getSimpleName();

	/**
	 * <pre>
	 * BillLearinigDiscountCsvクラスの宣言。
	 * </pre>
	 */
	private final BillLearinigDiscountCsv billLearinigDiscount;

	/**
	 * <pre>
	 * コンストラクタ。
	 * </pre>
	 */
	public BillLearinigDiscountCsvDao() {
		super();
		billLearinigDiscount = new BillLearinigDiscountCsv();
	}

	/**
	 * <pre>
	 * 入力用区切り文字を設定する。
	 * このDAOに関連する入力クラスの区切り文字を変更する。
	 * レコードデータは初期化される。
	 * </pre>
	 * @param delim
	 *   区切り文字
	 */
	public final void setDelimiter(final String delim) {
		billLearinigDiscount.setDelimiter(delim);
	}

	/**
	 * <pre>
	 * ファイルより、1件読み出す。
	 * </pre>
	 * @return BillLearinigDiscountCsv
	 *   新しいデータがない場合、nullを返す
	 * @throws CsvFileAccessException
	 *   CSVファイル読込みエラー発生
	 */
	public final BillLearinigDiscountCsv readLine()
		throws CsvFileAccessException {
		try {
			if (readLine(billLearinigDiscount)) {
				return billLearinigDiscount;
			} else {
				return null;
			}
		} catch (CsvFileNotFoundException | CsvSecurityException
				| CsvUnsupportedEncodingException | CsvIOException e) {
			throw new CsvFileAccessException(e);
		}
	}

	/**
	 * <pre>
	 * ファイル(CSV)に、1件書き込む。
	 * </pre>
	 * @param pRecord
	 *   書き込みデータ情報
	 * @throws CsvFileAccessException
	 *   CSVファイルアクセスエラー発生
	 * @throws CsvIOException
	 *   CSVファイル書込みエラー発生
	 */
	public final void writeData(final BillLearinigDiscountCsv pRecord)
		throws CsvFileAccessException, CsvIOException {
		try {
			writeLine(pRecord);
		} catch (CsvFileNotFoundException | CsvSecurityException | CsvUnsupportedEncodingException e) {
			throw new CsvFileAccessException(e);
		}
	}
}

//dao里的
package jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.biladpt2501002;

import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvFileAccess;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvFileAccessException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvFileNotFoundException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvIOException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvSecurityException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvUnsupportedEncodingException;

/**
 * <pre>
 * CSVファイルアクセス BillLearinigDiscountCsv のDAO実装クラス。
 * BillLearinigDiscountCsvDao。
 * </pre>
 * @author DHC
 * @version 2023/05/10 自動生成
 */
public class BillLearinigDiscountCsvDao extends CsvFileAccess {

	/**
	 * <pre>
	 * データアクセスオブジェクト名。
	 * </pre>
	 */
	public static final String DAO_NAME = BillLearinigDiscountCsvDao.class.getSimpleName();

	/**
	 * <pre>
	 * BillLearinigDiscountCsvクラスの宣言。
	 * </pre>
	 */
	private final BillLearinigDiscountCsv billLearinigDiscount;

	/**
	 * <pre>
	 * コンストラクタ。
	 * </pre>
	 */
	public BillLearinigDiscountCsvDao() {
		super();
		billLearinigDiscount = new BillLearinigDiscountCsv();
	}

	/**
	 * <pre>
	 * 入力用区切り文字を設定する。
	 * このDAOに関連する入力クラスの区切り文字を変更する。
	 * レコードデータは初期化される。
	 * </pre>
	 * @param delim
	 *   区切り文字
	 */
	public final void setDelimiter(final String delim) {
		billLearinigDiscount.setDelimiter(delim);
	}

	/**
	 * <pre>
	 * ファイルより、1件読み出す。
	 * </pre>
	 * @return BillLearinigDiscountCsv
	 *   新しいデータがない場合、nullを返す
	 * @throws CsvFileAccessException
	 *   CSVファイル読込みエラー発生
	 */
	public final BillLearinigDiscountCsv readLine()
		throws CsvFileAccessException {
		try {
			if (readLine(billLearinigDiscount)) {
				return billLearinigDiscount;
			} else {
				return null;
			}
		} catch (CsvFileNotFoundException | CsvSecurityException
				| CsvUnsupportedEncodingException | CsvIOException e) {
			throw new CsvFileAccessException(e);
		}
	}

	/**
	 * <pre>
	 * ファイル(CSV)に、1件書き込む。
	 * </pre>
	 * @param pRecord
	 *   書き込みデータ情報
	 * @throws CsvFileAccessException
	 *   CSVファイルアクセスエラー発生
	 * @throws CsvIOException
	 *   CSVファイル書込みエラー発生
	 */
	public final void writeData(final BillLearinigDiscountCsv pRecord)
		throws CsvFileAccessException, CsvIOException {
		try {
			writeLine(pRecord);
		} catch (CsvFileNotFoundException | CsvSecurityException | CsvUnsupportedEncodingException e) {
			throw new CsvFileAccessException(e);
		}
	}
}
