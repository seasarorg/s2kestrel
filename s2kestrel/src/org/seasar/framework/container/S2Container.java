package org.seasar.framework.container;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * コンポーネントを管理するDIコンテナのインターフェースです。
 *
 * @author higa
 */
public interface S2Container extends MetaDefAware {

	/**
     * キーを指定してコンポーネントを取得します。
     * キーが文字列の場合、一致するコンポーネント名を持つコンポーネントを
     * 取得します。
     * キーがクラスまたはインターフェースの場合、
     * 「コンポーネント instanceof キー」
     * を満たすコンポーネントを取得します。
     *
     * @param componentKey コンポーネントを取得するためのキー
     * @return コンポーネント
     * @throws ComponentNotFoundRuntimeException コンポーネントが見つからない場合
     * @throws TooManyRegistrationRuntimeException 同じ名前、または同じクラスに複数のコンポーネントが登録されている場合
     * @throws CyclicReferenceRuntimeException constructor injectionでコンポーネントの参照が循環している場合
     */
	public Object getComponent(Object componentKey)
		throws
			ComponentNotFoundRuntimeException,
			TooManyRegistrationRuntimeException,
			CyclicReferenceRuntimeException;

	/**
     * キーを指定してコンポーネントを検索します。
     * キーが文字列の場合、一致するコンポーネント名を持つコンポーネントを
     * 検索します。
     * キーがクラスまたはインターフェースの場合、
     * 「コンポーネント instanceof キー」
     * を満たすコンポーネントを検索します。
     *
     * @param componentKey コンポーネントを取得するためのキー
     * @return コンポーネントの配列。キーに対応するコンポーネントが存在しない場合は空の配列を返します。
     * @throws CyclicReferenceRuntimeException constructor injectionでコンポーネントの参照が循環している場合
     */
	public Object[] findComponents(Object componentKey)
		throws
			CyclicReferenceRuntimeException;

	/**
     * 外部コンポーネントにセッター・インジェクション、メソッド・イン
     * ジェクションを実行します。
     * 外部コンポーネントのクラスをキーとしてコンポーネント定義を取得します。
     * instanceモードが"outer"と定義されたコンポーネントのみ有効です。
     * 「コンポーネント instanceof 外部コンポーネント.getClass()」
     * を満たす外部コンポーネント定義を利用します。
     *
     * @param outerComponent 外部コンポーネント
     * @throws ClassUnmatchRuntimeException 適合する外部コンポーネント定義が見つからない場合
     */
	public void injectDependency(Object outerComponent)
		throws ClassUnmatchRuntimeException;

	/**
     * 外部コンポーネントにセッター・インジェクション、メソッド・イン
     * ジェクションを実行します。
     * componentClassをキーとしてコンポーネント定義を取得します。
     * instanceモードが"outer"と定義されたコンポーネントのみ有効です。
     * 「コンポーネント instanceof 外部コンポーネント」
     * を満たす外部コンポーネント定義を利用します。
     *
     * @param outerComponent 外部コンポーネント
     * @param componentClass 外部コンポーネント定義のキー (クラス)
     * @throws ClassUnmatchRuntimeException 「外部コンポーネント instanceof 取得したコンポーネントのクラス」がfalseを返す場合
     */
	public void injectDependency(Object outerComponent, Class componentClass)
		throws ClassUnmatchRuntimeException;

	/**
     * 外部コンポーネントにセッター・インジェクション、メソッド・イン
     * ジェクションを実行します。
     * componentClassをキーとしてコンポーネント定義を取得します。
     * instanceモードが"outer"と定義されたコンポーネントのみ有効です。
     * 「コンポーネント instanceof 外部コンポーネント」
     * を満たす外部コンポーネント定義を利用します。
     *
     * @param outerComponent 外部コンポーネント
     * @param componentName 外部コンポーネント定義のキー (名前)
     * @throws ClassUnmatchRuntimeException 「外部コンポーネント instanceof 取得したコンポーネントのクラス」がfalseを返す場合
     */
	public void injectDependency(Object outerComponent, String componentName)
		throws ClassUnmatchRuntimeException;

	/**
     * オブジェクトをコンポーネントとして登録します。
     * キーはオブジェクトのクラスになります。
     *
     * @param component コンポーネントとして登録するオブジェクト
     */
	public void register(Object component);
	
	/**
     * オブジェクトを名前付きコンポーネントとして登録します。
     *
     * @param component コンポーネントとして登録するオブジェクト
     * @param componentName コンポーネント名
     */
	public void register(Object component, String componentName);

	/**
     * クラスをコンポーネント定義として登録します。
     *
     * @param componentClass コンポーネントのクラス
     */
	public void register(Class componentClass);

	/**
     * クラスを名前付きコンポーネント定義として登録します。
     *
     * @param componentClass コンポーネントのクラス
     * @param componentName コンポーネントの名前
     */
	public void register(Class componentClass, String componentName);

	/**
     * コンポーネント定義を登録します。
     *
     * @param componentDef 登録するコンポーネント定義
     */
	public void register(ComponentDef componentDef);

	/**
     * コンポーネント定義の数を取得します。
     *
     * @return コンポーネント定義の数
     */
	public int getComponentDefSize();

	/**
     * 番号を指定してコンポーネント定義を取得します。
     *
     * @param index 番号
     * @return コンポーネント定義
     */
	public ComponentDef getComponentDef(int index);

	/**
     * 指定したキーに対応するコンポーネント定義を取得します。
     *
     * @param componentKey キー
     * @return コンポーネント定義
     * @throws ComponentNotFoundRuntimeException コンポーネント定義が見つからない場合
     */
	public ComponentDef getComponentDef(Object componentKey)
		throws ComponentNotFoundRuntimeException;

	/**
     * 指定したキーに対応するコンポーネント定義を検索します。
     *
     * @param componentKey キー
     * @return コンポーネント定義の配列。キーに対応するコンポーネントが存在しない場合は空の配列を返します。
     */
	public ComponentDef[] findComponentDefs(Object componentKey);

	/**
     * 指定したキーに対応するコンポーネント定義を持つどうか判定します。
     *
     * @param componentKey キー
     * @return 存在するならtrue
     */
	public boolean hasComponentDef(Object componentKey);
	
	/**
     * rootのコンテナで、pathに対応するコンテナが既にロードされて
     * いるかを返します。
     *
     * @param path パス
     * @return ロードされているならtrue
     */
	public boolean hasDescendant(String path);
	
	/**
     * rootのコンテナで、指定したパスに対応するロード済みのコンテナを取得します。
     *
     * @param path パス
     * @return コンテナ
     * @throws ContainerNotRegisteredRuntimeException コンテナが見つからない場合
     */
	public S2Container getDescendant(String path)
		throws ContainerNotRegisteredRuntimeException;
	
	/**
     * rootのコンテナに、ロード済みのコンテナを登録します。
     *
     * @param descendant ロード済みのコンテナ
     */
	public void registerDescendant(S2Container descendant);

	/**
     * 子コンテナをincludeします。
     *
     * @param child includeする子コンテナ
     */
	public void include(S2Container child);
	
	/**
     * 子コンテナの数を取得します。
     *
     * @return 子コンテナの数
     */
	public int getChildSize();
	
	/**
     * 番号を指定して子コンテナを取得します。
     *
     * @param index 子コンテナの番号
     * @return 子コンテナ
     */
	public S2Container getChild(int index);

	/**
     * コンテナを初期化します。
     * 子コンテナを持つ場合、子コンテナを全て初期化した後、自分を初期化します。
     */
	public void init();

	/**
     * コンテナの終了処理をおこないます。
     * 子コンテナを持つ場合、自分の終了処理を実行した後、
     * 子コンテナ全ての終了処理を行います。
     */
	public void destroy();
	
	/**
     * 名前空間を取得します。
     *
     * @return 名前空間
     */
	public String getNamespace();
	
	/**
     * 名前空間をセットします。
     *
     * @param namespace セットする名前空間
     */
	public void setNamespace(String namespace);
	
	/**
     * 設定ファイルのパスを取得します。
     *
     * @return 設定ファイルのパス
     */
	public String getPath();
	
	/**
     * 設定ファイルのパスをセットします。
     *
     * @param path セットする設定ファイルのパス
     */
	public void setPath(String path);

	/**
     * ルートのコンテナを取得します。
     *
     * @return ルートのコンテナ
     */
	public S2Container getRoot();
	
	/**
     * ルートのコンテナをセットします。
     *
     * @param root セットするルートのコンテナ
     */
	public void setRoot(S2Container root);
	
	/**
     * リクエストを取得します。
     *
     * @return リクエスト
     */
	public HttpServletRequest getRequest();
	
	/**
     * リクエストをセットします。リクエストはTheadLocalで管理されます。
     *
     * @param request セットするリクエスト
     */
	public void setRequest(HttpServletRequest request);
	
	/**
     * セッションを取得します。
     *
     * @return セッション
     */
	public HttpSession getSession();
	
	/**
     * レスポンスを取得します。
     *
     * @return レスポンス
     */
	public HttpServletResponse getResponse();
	
	/**
     * レスポンスをセットします。レスポンスはTheadLocalで管理されます。
     *
     * @param response セットするレスポンス
     */
	public void setResponse(HttpServletResponse response);
	
	/**
     * サーブレットコンテキストを取得します。
     *
     * @return サーブレットコンテキスト
     */
	public ServletContext getServletContext();
	
	/**
     * サーブレットコンテキストをセットします。
     *
     * @param servletContext セットするサーブレットコンテキスト
     */
	public void setServletContext(ServletContext servletContext);
}
