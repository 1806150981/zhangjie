//读文件
BillLearinigDiscountCsv billLearinigDiscountCsv = null

List<BillLearinigDiscountCsv> sourceData = new ArrayList();
while((billLearinigDiscountCsv=billLearinigDiscountCsvDao.readLine())!=null){

if(billLearinigDiscountCsv!=null){
sourceData.add(billLearinigDiscountCsv)
}
    
}

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

//我的主逻辑
package jp.co.softbank.sbm.chronos.billing.batch.chmb.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.softbank.chronos.commonparts.utility.DateFormatter;
import jp.co.softbank.chronos.core.chmb.adapter.AdapterDto;
import jp.co.softbank.chronos.core.chmb.exception.ChMBSystemException;
import jp.co.softbank.chronos.core.message.ChMessage;
import jp.co.softbank.chronos.utility.DateFormatUtility;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.ChMBNomalBase;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.biladpt2501002.BillBaTargetCalcBsCsvDao;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.biladpt2501002.BillBatTargetCalcOutCsvDao;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.biladpt2501002.BillLearinigDiscountCsv;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.biladpt2501002.BillLearinigDiscountCsvDao;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.bilhtn51100.MonCompensationBgResultCorrectCsv;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvFileAccessException;
import jp.co.softbank.sbm.chronos.billing.batch.chmb.common.file.csv.common.CsvIOException;
import jp.co.softbank.sbm.chronos.billing.batch.common.constant.CodeConstant;
import jp.co.softbank.sbm.chronos.billing.batch.common.constant.CodeConstant.NumConstant;
import jp.co.softbank.sbm.chronos.billing.batch.common.constant.CodeConstant.StringConstant;
import jp.co.softbank.sbm.chronos.billing.batch.common.constant.ErrorConstant.ErrBILCdeConstant;
import jp.co.softbank.sbm.chronos.billing.batch.common.message.BillLearinigDiscountMsg;
import jp.co.softbank.sbm.chronos.billing.batch.common.message.JasperBillGroupMsg;
import jp.co.softbank.sbm.chronos.billing.batch.common.message.MonCompensationBgResultMsg;
import jp.co.softbank.sbm.chronos.commonparts.kvs.jdgdto.bl.CompensationBgValue;


/**
 * 
 * 請求練習用 (ChMB)
 * 
 * @author DHC
 * 
 */

public class BILTEXT501002 extends ChMBNomalBase{

	/** 再計算対象（中間）ファイル */
	private BillBaTargetCalcBsCsvDao billBaTargetCalcBsCsvDao = null;

	/** 再計算対象外（中間）ファイル */
	private BillBatTargetCalcOutCsvDao billBatTargetCalcOutCsvDao = null;

	/** 請求練習用対象ファイル  */
	private BillLearinigDiscountCsvDao billLearinigDiscountCsvDao = null;

	/** OKファイル */
	private BillLearinigDiscountCsvDao okDao = null;

	/** NGファイル */
	private BillLearinigDiscountCsvDao ngDao = null;
	/**
	 * 
	 * Chmb初期処理。
	 * 
	 * @param args 起動引数
	 */
	@Override
	public void doInit(List<String> args) {
		// JOBID
		super.setJobId(args.get(NumConstant.CHMB_ARGS_JOB_ID));
		// 再計算対象（中間）ファイルパス
		String inputPath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_1);
		// 再計算対象外（中間）ファイルパス
		String outoftargetOutputPath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_2);
		// 請求練習用対象ファイルパス
		String targetFilePath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_3);
		// OKファイルパス
		String okFilePath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_4);
		// NGファイルパス
		String ngFilePath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_5);

		// 再計算対象（中間）ファイル
		billBaTargetCalcBsCsvDao = new BillBaTargetCalcBsCsvDao();
		billBaTargetCalcBsCsvDao.setFileName(inputPath, CodeConstant.StringConstant.CHARSET_NAME, false);

		// 再計算対象外（中間）ファイル
		billBatTargetCalcOutCsvDao = new BillBatTargetCalcOutCsvDao();
		billBatTargetCalcOutCsvDao.setFileName(outoftargetOutputPath, CodeConstant.StringConstant.CHARSET_NAME, true);

		// 請求練習用対象ファイル
		billLearinigDiscountCsvDao = new BillLearinigDiscountCsvDao();
		billLearinigDiscountCsvDao.setFileName(targetFilePath, CodeConstant.StringConstant.CHARSET_NAME, true);

		// OKファイル
		okDao = new BillLearinigDiscountCsvDao();
		okDao.setFileName(okFilePath, CodeConstant.StringConstant.CHARSET_NAME, true);

		// NGファイル
		ngDao = new BillLearinigDiscountCsvDao();
		ngDao.setFileName(ngFilePath, CodeConstant.StringConstant.CHARSET_NAME, true);
	}

	/**
	 * 起動パラメータチェック。
	 * 
	 * @param args 起動引数
	 */
	@Override
	public void checkParameters(List<String> args) {
		
		/** 起動引数の個数。 */
		int initParamNum = 6;

		if (args.size() != initParamNum) {
			// 起動引数の個数チェック
			throw new ChMBSystemException(ErrBILCdeConstant.ERROR_MESSAGE_BIL_E509123);
		}
		
	}

	/**
	 * アダプタ主処理。
	 * 
	 * @param args 起動引数
	 */
	@Override
	public void doMain(List<String> args) {
		try {
			BillLearinigDiscountCsv billLearinigDiscountCsv = billLearinigDiscountCsvDao.readLine();
			
			List<BillLearinigDiscountCsv> billLearinigDiscountCsvList = new ArrayList();
			while((billLearinigDiscountCsv = billLearinigDiscountCsvDao.readLine())!=null){
				if(billLearinigDiscountCsv!=null){
					billLearinigDiscountCsvList.add(billLearinigDiscountCsv);
				}
				// msgを作成する
				BillLearinigDiscountMsg msg = new BillLearinigDiscountMsg();
				
				List<BillLearinigDiscountCsv> billLearinigDiscountCsvNew = new ArrayList();
				
				
			}

			
			
		} catch (CsvFileAccessException e) {
			throw new ChMBSystemException(ErrBILCdeConstant.ERROR_MESSAGE_BIL_E509125, e, jobId);
		}
		
	}

	/**
	 * Chmb終了処理
	 */
	@Override
	public void doTerminate() {
		try {
			billLearinigDiscountCsvDao.close();
			billBaTargetCalcBsCsvDao.close();
			billBatTargetCalcOutCsvDao.close();
			okDao.close();
			ngDao.close();
		} catch (CsvIOException e) {
			throw new ChMBSystemException(ErrBILCdeConstant.ERROR_MESSAGE_BIL_E509125, e, jobId);
		}
		// 終了ログを設定する。
		processResultLogOutput();
	}

	/**
	 * @param adapterDto
	 * @param response
	 */
	@Override
	public void doResponse(AdapterDto adapterDto, ChMessage response) {
		try {

		} catch (Exception e) {
			throw new ChMBSystemException(ErrBILCdeConstant.ERROR_MESSAGE_BIL_E509125, e, jobId);
		}
	}


	/**
	 * NGファイル出力
	 * 
	 * @param adapterDto CCEへ送信したAdapterDtoインスタンス
	 * @param error CCEで発生したエラー
	 */
	@Override
	public void doError(AdapterDto adapterDto, Throwable error) {
		try {
			BillLearinigDiscountMsg msg = (BillLearinigDiscountMsg)adapterDto.getValue();
			ngDao.writeData(msgToInputFile(msg));
		} catch (Exception e) {
			throw new ChMBSystemException(ErrBILCdeConstant.ERROR_MESSAGE_BIL_E509125, e, jobId);
		}
	}
	
	/**
	 * ChMessage設定詳細
	 *
	 */
	private BillLearinigDiscountCsv msgToInputFile(BillLearinigDiscountMsg outData) {

		// メッセージの生成
		BillLearinigDiscountCsv outCsv = new BillLearinigDiscountCsv();
		
		outCsv.setBillMonth(outData.getBillMonth()); // 請求年月
		outCsv.setBillCycleId(outData.getBillCycleId()); // 請求群ID
		outCsv.setBillGroupId(outData.getBillGroupId()); // 請求グループＩＤ
		outCsv.setServiceId(outData.getServiceId()); // サービスＩＤ
		outCsv.setBillamt(outData.getBillamt()); // 請求発生額
		outCsv.setBillLimitAmt(outData.getBillLimitAmt()); // 上限額
		
		return outCsv;
	}
	public boolean Renewmessage(BillLearinigDiscountCsv item,List<BillLearinigDiscountCsv> billLearinigDiscountCsvNew) {
		return 
	}
}
	
	
	
