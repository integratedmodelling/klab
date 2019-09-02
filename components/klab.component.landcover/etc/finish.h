//----------------------------------------------------------------------------
#ifndef finishH
#define finishH
//----------------------------------------------------------------------------
#include <vcl\System.hpp>
#include <vcl\Windows.hpp>
#include <vcl\SysUtils.hpp>
#include <vcl\Classes.hpp>
#include <vcl\Graphics.hpp>
#include <vcl\StdCtrls.hpp>
#include <vcl\Forms.hpp>
#include <vcl\Controls.hpp>
#include <vcl\Buttons.hpp>
#include <vcl\ExtCtrls.hpp>
//----------------------------------------------------------------------------
class Tfinishbox : public TForm
{
__published:        
	TButton *OKBtn;
	TBevel *Bevel1;
        TLabel *Label1;
private:
public:
	virtual __fastcall Tfinishbox(TComponent* AOwner);
};
//----------------------------------------------------------------------------
extern PACKAGE Tfinishbox *finishbox;
//----------------------------------------------------------------------------
#endif    
