package datahub.step;

import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * ログ用クラス
 */
@Service
public class BaseStep {

	public MessageSourceAccessor msa;

	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(new Object() {
	}.getClass().getEnclosingClass());

	/**
	 * コンストラクタ
	 */
	public BaseStep(MessageSource ms) {
		this.msa = new MessageSourceAccessor(ms);
	}
}
